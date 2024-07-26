import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAlloyHttp } from '../alloy-http.model';
import { AlloyHttpService } from '../service/alloy-http.service';

@Component({
  standalone: true,
  templateUrl: './alloy-http-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AlloyHttpDeleteDialogComponent {
  alloyHttp?: IAlloyHttp;

  protected alloyHttpService = inject(AlloyHttpService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alloyHttpService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
