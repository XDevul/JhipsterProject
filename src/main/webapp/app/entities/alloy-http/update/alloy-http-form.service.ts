import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAlloyHttp, NewAlloyHttp } from '../alloy-http.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAlloyHttp for edit and NewAlloyHttpFormGroupInput for create.
 */
type AlloyHttpFormGroupInput = IAlloyHttp | PartialWithRequiredKeyOf<NewAlloyHttp>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAlloyHttp | NewAlloyHttp> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type AlloyHttpFormRawValue = FormValueOf<IAlloyHttp>;

type NewAlloyHttpFormRawValue = FormValueOf<NewAlloyHttp>;

type AlloyHttpFormDefaults = Pick<NewAlloyHttp, 'id' | 'createTime' | 'updateTime'>;

type AlloyHttpFormGroupContent = {
  id: FormControl<AlloyHttpFormRawValue['id'] | NewAlloyHttp['id']>;
  name: FormControl<AlloyHttpFormRawValue['name']>;
  address: FormControl<AlloyHttpFormRawValue['address']>;
  modul: FormControl<AlloyHttpFormRawValue['modul']>;
  api: FormControl<AlloyHttpFormRawValue['api']>;
  env: FormControl<AlloyHttpFormRawValue['env']>;
  hostname: FormControl<AlloyHttpFormRawValue['hostname']>;
  createTime: FormControl<AlloyHttpFormRawValue['createTime']>;
  updateTime: FormControl<AlloyHttpFormRawValue['updateTime']>;
};

export type AlloyHttpFormGroup = FormGroup<AlloyHttpFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AlloyHttpFormService {
  createAlloyHttpFormGroup(alloyHttp: AlloyHttpFormGroupInput = { id: null }): AlloyHttpFormGroup {
    const alloyHttpRawValue = this.convertAlloyHttpToAlloyHttpRawValue({
      ...this.getFormDefaults(),
      ...alloyHttp,
    });
    return new FormGroup<AlloyHttpFormGroupContent>({
      id: new FormControl(
        { value: alloyHttpRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(alloyHttpRawValue.name, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      address: new FormControl(alloyHttpRawValue.address, {
        validators: [Validators.required, Validators.maxLength(2048)],
      }),
      modul: new FormControl(alloyHttpRawValue.modul, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      api: new FormControl(alloyHttpRawValue.api, {
        validators: [Validators.maxLength(20)],
      }),
      env: new FormControl(alloyHttpRawValue.env, {
        validators: [Validators.maxLength(20)],
      }),
      hostname: new FormControl(alloyHttpRawValue.hostname, {
        validators: [Validators.maxLength(2048)],
      }),
      createTime: new FormControl(alloyHttpRawValue.createTime, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(alloyHttpRawValue.updateTime),
    });
  }

  getAlloyHttp(form: AlloyHttpFormGroup): IAlloyHttp | NewAlloyHttp {
    return this.convertAlloyHttpRawValueToAlloyHttp(form.getRawValue() as AlloyHttpFormRawValue | NewAlloyHttpFormRawValue);
  }

  resetForm(form: AlloyHttpFormGroup, alloyHttp: AlloyHttpFormGroupInput): void {
    const alloyHttpRawValue = this.convertAlloyHttpToAlloyHttpRawValue({ ...this.getFormDefaults(), ...alloyHttp });
    form.reset(
      {
        ...alloyHttpRawValue,
        id: { value: alloyHttpRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AlloyHttpFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertAlloyHttpRawValueToAlloyHttp(rawAlloyHttp: AlloyHttpFormRawValue | NewAlloyHttpFormRawValue): IAlloyHttp | NewAlloyHttp {
    return {
      ...rawAlloyHttp,
      createTime: dayjs(rawAlloyHttp.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawAlloyHttp.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertAlloyHttpToAlloyHttpRawValue(
    alloyHttp: IAlloyHttp | (Partial<NewAlloyHttp> & AlloyHttpFormDefaults),
  ): AlloyHttpFormRawValue | PartialWithRequiredKeyOf<NewAlloyHttpFormRawValue> {
    return {
      ...alloyHttp,
      createTime: alloyHttp.createTime ? alloyHttp.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: alloyHttp.updateTime ? alloyHttp.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
