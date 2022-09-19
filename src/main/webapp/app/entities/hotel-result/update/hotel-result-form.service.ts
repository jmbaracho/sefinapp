import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHotelResult, NewHotelResult } from '../hotel-result.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotelResult for edit and NewHotelResultFormGroupInput for create.
 */
type HotelResultFormGroupInput = IHotelResult | PartialWithRequiredKeyOf<NewHotelResult>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHotelResult | NewHotelResult> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type HotelResultFormRawValue = FormValueOf<IHotelResult>;

type NewHotelResultFormRawValue = FormValueOf<NewHotelResult>;

type HotelResultFormDefaults = Pick<NewHotelResult, 'id' | 'createdAt' | 'updatedAt'>;

type HotelResultFormGroupContent = {
  id: FormControl<HotelResultFormRawValue['id'] | NewHotelResult['id']>;
  name: FormControl<HotelResultFormRawValue['name']>;
  address: FormControl<HotelResultFormRawValue['address']>;
  starsCount: FormControl<HotelResultFormRawValue['starsCount']>;
  rating: FormControl<HotelResultFormRawValue['rating']>;
  createdAt: FormControl<HotelResultFormRawValue['createdAt']>;
  updatedAt: FormControl<HotelResultFormRawValue['updatedAt']>;
  task: FormControl<HotelResultFormRawValue['task']>;
  agent: FormControl<HotelResultFormRawValue['agent']>;
  company: FormControl<HotelResultFormRawValue['company']>;
};

export type HotelResultFormGroup = FormGroup<HotelResultFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelResultFormService {
  createHotelResultFormGroup(hotelResult: HotelResultFormGroupInput = { id: null }): HotelResultFormGroup {
    const hotelResultRawValue = this.convertHotelResultToHotelResultRawValue({
      ...this.getFormDefaults(),
      ...hotelResult,
    });
    return new FormGroup<HotelResultFormGroupContent>({
      id: new FormControl(
        { value: hotelResultRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(hotelResultRawValue.name),
      address: new FormControl(hotelResultRawValue.address),
      starsCount: new FormControl(hotelResultRawValue.starsCount),
      rating: new FormControl(hotelResultRawValue.rating),
      createdAt: new FormControl(hotelResultRawValue.createdAt),
      updatedAt: new FormControl(hotelResultRawValue.updatedAt),
      task: new FormControl(hotelResultRawValue.task),
      agent: new FormControl(hotelResultRawValue.agent),
      company: new FormControl(hotelResultRawValue.company),
    });
  }

  getHotelResult(form: HotelResultFormGroup): IHotelResult | NewHotelResult {
    return this.convertHotelResultRawValueToHotelResult(form.getRawValue() as HotelResultFormRawValue | NewHotelResultFormRawValue);
  }

  resetForm(form: HotelResultFormGroup, hotelResult: HotelResultFormGroupInput): void {
    const hotelResultRawValue = this.convertHotelResultToHotelResultRawValue({ ...this.getFormDefaults(), ...hotelResult });
    form.reset(
      {
        ...hotelResultRawValue,
        id: { value: hotelResultRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HotelResultFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertHotelResultRawValueToHotelResult(
    rawHotelResult: HotelResultFormRawValue | NewHotelResultFormRawValue
  ): IHotelResult | NewHotelResult {
    return {
      ...rawHotelResult,
      createdAt: dayjs(rawHotelResult.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawHotelResult.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertHotelResultToHotelResultRawValue(
    hotelResult: IHotelResult | (Partial<NewHotelResult> & HotelResultFormDefaults)
  ): HotelResultFormRawValue | PartialWithRequiredKeyOf<NewHotelResultFormRawValue> {
    return {
      ...hotelResult,
      createdAt: hotelResult.createdAt ? hotelResult.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: hotelResult.updatedAt ? hotelResult.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
