import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AlloyHttpService } from '../service/alloy-http.service';
import { IAlloyHttp } from '../alloy-http.model';
import { AlloyHttpFormService } from './alloy-http-form.service';

import { AlloyHttpUpdateComponent } from './alloy-http-update.component';

describe('AlloyHttp Management Update Component', () => {
  let comp: AlloyHttpUpdateComponent;
  let fixture: ComponentFixture<AlloyHttpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let alloyHttpFormService: AlloyHttpFormService;
  let alloyHttpService: AlloyHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AlloyHttpUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AlloyHttpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AlloyHttpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    alloyHttpFormService = TestBed.inject(AlloyHttpFormService);
    alloyHttpService = TestBed.inject(AlloyHttpService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const alloyHttp: IAlloyHttp = { id: 456 };

      activatedRoute.data = of({ alloyHttp });
      comp.ngOnInit();

      expect(comp.alloyHttp).toEqual(alloyHttp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlloyHttp>>();
      const alloyHttp = { id: 123 };
      jest.spyOn(alloyHttpFormService, 'getAlloyHttp').mockReturnValue(alloyHttp);
      jest.spyOn(alloyHttpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alloyHttp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alloyHttp }));
      saveSubject.complete();

      // THEN
      expect(alloyHttpFormService.getAlloyHttp).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(alloyHttpService.update).toHaveBeenCalledWith(expect.objectContaining(alloyHttp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlloyHttp>>();
      const alloyHttp = { id: 123 };
      jest.spyOn(alloyHttpFormService, 'getAlloyHttp').mockReturnValue({ id: null });
      jest.spyOn(alloyHttpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alloyHttp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alloyHttp }));
      saveSubject.complete();

      // THEN
      expect(alloyHttpFormService.getAlloyHttp).toHaveBeenCalled();
      expect(alloyHttpService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlloyHttp>>();
      const alloyHttp = { id: 123 };
      jest.spyOn(alloyHttpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alloyHttp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(alloyHttpService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
