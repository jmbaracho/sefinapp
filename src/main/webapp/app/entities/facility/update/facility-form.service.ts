import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFacility, NewFacility } from '../facility.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFacility for edit and NewFacilityFormGroupInput for create.
 */
type FacilityFormGroupInput = IFacility | PartialWithRequiredKeyOf<NewFacility>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFacility | NewFacility> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type FacilityFormRawValue = FormValueOf<IFacility>;

type NewFacilityFormRawValue = FormValueOf<NewFacility>;

type FacilityFormDefaults = Pick<NewFacility, 'id' | 'createdAt' | 'updatedAt'>;

type FacilityFormGroupContent = {
  id: FormControl<FacilityFormRawValue['id'] | NewFacility['id']>;
  name: FormControl<FacilityFormRawValue['name']>;
  createdAt: FormControl<FacilityFormRawValue['createdAt']>;
  updatedAt: FormControl<FacilityFormRawValue['updatedAt']>;
};

export type FacilityFormGroup = FormGroup<FacilityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FacilityFormService {
  createFacilityFormGroup(facility: FacilityFormGroupInput = { id: null }): FacilityFormGroup {
    const facilityRawValue = this.convertFacilityToFacilityRawValue({
      ...this.getFormDefaults(),
      ...facility,
    });
    return new FormGroup<FacilityFormGroupContent>({
      id: new FormControl(
        { value: facilityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(facilityRawValue.name),
      createdAt: new FormControl(facilityRawValue.createdAt),
      updatedAt: new FormControl(facilityRawValue.updatedAt),
    });
  }

  getFacility(form: FacilityFormGroup): IFacility | NewFacility {
    return this.convertFacilityRawValueToFacility(form.getRawValue() as FacilityFormRawValue | NewFacilityFormRawValue);
  }

  resetForm(form: FacilityFormGroup, facility: FacilityFormGroupInput): void {
    const facilityRawValue = this.convertFacilityToFacilityRawValue({ ...this.getFormDefaults(), ...facility });
    form.reset(
      {
        ...facilityRawValue,
        id: { value: facilityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FacilityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertFacilityRawValueToFacility(rawFacility: FacilityFormRawValue | NewFacilityFormRawValue): IFacility | NewFacility {
    return {
      ...rawFacility,
      createdAt: dayjs(rawFacility.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawFacility.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertFacilityToFacilityRawValue(
    facility: IFacility | (Partial<NewFacility> & FacilityFormDefaults)
  ): FacilityFormRawValue | PartialWithRequiredKeyOf<NewFacilityFormRawValue> {
    return {
      ...facility,
      createdAt: facility.createdAt ? facility.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: facility.updatedAt ? facility.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
