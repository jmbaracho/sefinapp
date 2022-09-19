import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISource, NewSource } from '../source.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISource for edit and NewSourceFormGroupInput for create.
 */
type SourceFormGroupInput = ISource | PartialWithRequiredKeyOf<NewSource>;

type SourceFormDefaults = Pick<NewSource, 'id' | 'enabled' | 'failed'>;

type SourceFormGroupContent = {
  id: FormControl<ISource['id'] | NewSource['id']>;
  url: FormControl<ISource['url']>;
  enabled: FormControl<ISource['enabled']>;
  failed: FormControl<ISource['failed']>;
  createdAt: FormControl<ISource['createdAt']>;
  updatedAt: FormControl<ISource['updatedAt']>;
  agent: FormControl<ISource['agent']>;
  company: FormControl<ISource['company']>;
};

export type SourceFormGroup = FormGroup<SourceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SourceFormService {
  createSourceFormGroup(source: SourceFormGroupInput = { id: null }): SourceFormGroup {
    const sourceRawValue = {
      ...this.getFormDefaults(),
      ...source,
    };
    return new FormGroup<SourceFormGroupContent>({
      id: new FormControl(
        { value: sourceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      url: new FormControl(sourceRawValue.url),
      enabled: new FormControl(sourceRawValue.enabled),
      failed: new FormControl(sourceRawValue.failed),
      createdAt: new FormControl(sourceRawValue.createdAt),
      updatedAt: new FormControl(sourceRawValue.updatedAt),
      agent: new FormControl(sourceRawValue.agent),
      company: new FormControl(sourceRawValue.company),
    });
  }

  getSource(form: SourceFormGroup): ISource | NewSource {
    return form.getRawValue() as ISource | NewSource;
  }

  resetForm(form: SourceFormGroup, source: SourceFormGroupInput): void {
    const sourceRawValue = { ...this.getFormDefaults(), ...source };
    form.reset(
      {
        ...sourceRawValue,
        id: { value: sourceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SourceFormDefaults {
    return {
      id: null,
      enabled: false,
      failed: false,
    };
  }
}
