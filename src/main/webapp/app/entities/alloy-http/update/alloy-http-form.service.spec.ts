import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../alloy-http.test-samples';

import { AlloyHttpFormService } from './alloy-http-form.service';

describe('AlloyHttp Form Service', () => {
  let service: AlloyHttpFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlloyHttpFormService);
  });

  describe('Service methods', () => {
    describe('createAlloyHttpFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAlloyHttpFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            modul: expect.any(Object),
            api: expect.any(Object),
            env: expect.any(Object),
            hostname: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
          }),
        );
      });

      it('passing IAlloyHttp should create a new form with FormGroup', () => {
        const formGroup = service.createAlloyHttpFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            modul: expect.any(Object),
            api: expect.any(Object),
            env: expect.any(Object),
            hostname: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
          }),
        );
      });
    });

    describe('getAlloyHttp', () => {
      it('should return NewAlloyHttp for default AlloyHttp initial value', () => {
        const formGroup = service.createAlloyHttpFormGroup(sampleWithNewData);

        const alloyHttp = service.getAlloyHttp(formGroup) as any;

        expect(alloyHttp).toMatchObject(sampleWithNewData);
      });

      it('should return NewAlloyHttp for empty AlloyHttp initial value', () => {
        const formGroup = service.createAlloyHttpFormGroup();

        const alloyHttp = service.getAlloyHttp(formGroup) as any;

        expect(alloyHttp).toMatchObject({});
      });

      it('should return IAlloyHttp', () => {
        const formGroup = service.createAlloyHttpFormGroup(sampleWithRequiredData);

        const alloyHttp = service.getAlloyHttp(formGroup) as any;

        expect(alloyHttp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAlloyHttp should not enable id FormControl', () => {
        const formGroup = service.createAlloyHttpFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAlloyHttp should disable id FormControl', () => {
        const formGroup = service.createAlloyHttpFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
