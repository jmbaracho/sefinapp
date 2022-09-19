import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHotelResultFacility, NewHotelResultFacility } from '../hotel-result-facility.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotelResultFacility for edit and NewHotelResultFacilityFormGroupInput for create.
 */
type HotelResultFacilityFormGroupInput = IHotelResultFacility | PartialWithRequiredKeyOf<NewHotelResultFacility>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHotelResultFacility | NewHotelResultFacility> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type HotelResultFacilityFormRawValue = FormValueOf<IHotelResultFacility>;

type NewHotelResultFacilityFormRawValue = FormValueOf<NewHotelResultFacility>;

type HotelResultFacilityFormDefaults = Pick<NewHotelResultFacility, 'id' | 'createdAt' | 'updatedAt'>;

type HotelResultFacilityFormGroupContent = {
  id: FormControl<HotelResultFacilityFormRawValue['id'] | NewHotelResultFacility['id']>;
  createdAt: FormControl<HotelResultFacilityFormRawValue['createdAt']>;
  updatedAt: FormControl<HotelResultFacilityFormRawValue['updatedAt']>;
  hotelResult: FormControl<HotelResultFacilityFormRawValue['hotelResult']>;
  facility: FormControl<HotelResultFacilityFormRawValue['facility']>;
};

export type HotelResultFacilityFormGroup = FormGroup<HotelResultFacilityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelResultFacilityFormService {
  createHotelResultFacilityFormGroup(hotelResultFacility: HotelResultFacilityFormGroupInput = { id: null }): HotelResultFacilityFormGroup {
    const hotelResultFacilityRawValue = this.convertHotelResultFacilityToHotelResultFacilityRawValue({
      ...this.getFormDefaults(),
      ...hotelResultFacility,
    });
    return new FormGroup<HotelResultFacilityFormGroupContent>({
      id: new FormControl(
        { value: hotelResultFacilityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      createdAt: new FormControl(hotelResultFacilityRawValue.createdAt),
      updatedAt: new FormControl(hotelResultFacilityRawValue.updatedAt),
      hotelResult: new FormControl(hotelResultFacilityRawValue.hotelResult),
      facility: new FormControl(hotelResultFacilityRawValue.facility),
    });
  }

  getHotelResultFacility(form: HotelResultFacilityFormGroup): IHotelResultFacility | NewHotelResultFacility {
    return this.convertHotelResultFacilityRawValueToHotelResultFacility(
      form.getRawValue() as HotelResultFacilityFormRawValue | NewHotelResultFacilityFormRawValue
    );
  }

  resetForm(form: HotelResultFacilityFormGroup, hotelResultFacility: HotelResultFacilityFormGroupInput): void {
    const hotelResultFacilityRawValue = this.convertHotelResultFacilityToHotelResultFacilityRawValue({
      ...this.getFormDefaults(),
      ...hotelResultFacility,
    });
    form.reset(
      {
        ...hotelResultFacilityRawValue,
        id: { value: hotelResultFacilityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HotelResultFacilityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertHotelResultFacilityRawValueToHotelResultFacility(
    rawHotelResultFacility: HotelResultFacilityFormRawValue | NewHotelResultFacilityFormRawValue
  ): IHotelResultFacility | NewHotelResultFacility {
    return {
      ...rawHotelResultFacility,
      createdAt: dayjs(rawHotelResultFacility.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawHotelResultFacility.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertHotelResultFacilityToHotelResultFacilityRawValue(
    hotelResultFacility: IHotelResultFacility | (Partial<NewHotelResultFacility> & HotelResultFacilityFormDefaults)
  ): HotelResultFacilityFormRawValue | PartialWithRequiredKeyOf<NewHotelResultFacilityFormRawValue> {
    return {
      ...hotelResultFacility,
      createdAt: hotelResultFacility.createdAt ? hotelResultFacility.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: hotelResultFacility.updatedAt ? hotelResultFacility.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
