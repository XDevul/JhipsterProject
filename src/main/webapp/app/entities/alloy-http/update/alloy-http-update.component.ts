import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAlloyHttp } from '../alloy-http.model';
import { AlloyHttpService } from '../service/alloy-http.service';
import { AlloyHttpFormService, AlloyHttpFormGroup } from './alloy-http-form.service';

@Component({
  standalone: true,
  selector: 'jhi-alloy-http-update',
  templateUrl: './alloy-http-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AlloyHttpUpdateComponent implements OnInit {
  isSaving = false;
  alloyHttp: IAlloyHttp | null = null;

  protected alloyHttpService = inject(AlloyHttpService);
  protected alloyHttpFormService = inject(AlloyHttpFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AlloyHttpFormGroup = this.alloyHttpFormService.createAlloyHttpFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alloyHttp }) => {
      this.alloyHttp = alloyHttp;
      if (alloyHttp) {
        this.updateForm(alloyHttp);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alloyHttp = this.alloyHttpFormService.getAlloyHttp(this.editForm);
    if (alloyHttp.id !== null) {
      this.subscribeToSaveResponse(this.alloyHttpService.update(alloyHttp));
    } else {
      this.subscribeToSaveResponse(this.alloyHttpService.create(alloyHttp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlloyHttp>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(alloyHttp: IAlloyHttp): void {
    this.alloyHttp = alloyHttp;
    this.alloyHttpFormService.resetForm(this.editForm, alloyHttp);
  }
}
