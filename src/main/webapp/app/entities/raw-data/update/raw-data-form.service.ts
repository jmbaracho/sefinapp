import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRawData, NewRawData } from '../raw-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRawData for edit and NewRawDataFormGroupInput for create.
 */
type RawDataFormGroupInput = IRawData | PartialWithRequiredKeyOf<NewRawData>;

type RawDataFormDefaults = Pick<NewRawData, 'id' | 'processed'>;

type RawDataFormGroupContent = {
  id: FormControl<IRawData['id'] | NewRawData['id']>;
  content: FormControl<IRawData['content']>;
  processed: FormControl<IRawData['processed']>;
  createdAt: FormControl<IRawData['createdAt']>;
  updatedAt: FormControl<IRawData['updatedAt']>;
  task: FormControl<IRawData['task']>;
};

export type RawDataFormGroup = FormGroup<RawDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RawDataFormService {
  createRawDataFormGroup(rawData: RawDataFormGroupInput = { id: null }): RawDataFormGroup {
    const rawDataRawValue = {
      ...this.getFormDefaults(),
      ...rawData,
    };
    return new FormGroup<RawDataFormGroupContent>({
      id: new FormControl(
        { value: rawDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      content: new FormControl(rawDataRawValue.content),
      processed: new FormControl(rawDataRawValue.processed),
      createdAt: new FormControl(rawDataRawValue.createdAt),
      updatedAt: new FormControl(rawDataRawValue.updatedAt),
      task: new FormControl(rawDataRawValue.task),
    });
  }

  getRawData(form: RawDataFormGroup): IRawData | NewRawData {
    return form.getRawValue() as IRawData | NewRawData;
  }

  resetForm(form: RawDataFormGroup, rawData: RawDataFormGroupInput): void {
    const rawDataRawValue = { ...this.getFormDefaults(), ...rawData };
    form.reset(
      {
        ...rawDataRawValue,
        id: { value: rawDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RawDataFormDefaults {
    return {
      id: null,
      processed: false,
    };
  }
}
