import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IScheduleExecution, NewScheduleExecution } from '../schedule-execution.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IScheduleExecution for edit and NewScheduleExecutionFormGroupInput for create.
 */
type ScheduleExecutionFormGroupInput = IScheduleExecution | PartialWithRequiredKeyOf<NewScheduleExecution>;

type ScheduleExecutionFormDefaults = Pick<NewScheduleExecution, 'id'>;

type ScheduleExecutionFormGroupContent = {
  id: FormControl<IScheduleExecution['id'] | NewScheduleExecution['id']>;
  createdAt: FormControl<IScheduleExecution['createdAt']>;
  updatedAt: FormControl<IScheduleExecution['updatedAt']>;
  schedule: FormControl<IScheduleExecution['schedule']>;
};

export type ScheduleExecutionFormGroup = FormGroup<ScheduleExecutionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ScheduleExecutionFormService {
  createScheduleExecutionFormGroup(scheduleExecution: ScheduleExecutionFormGroupInput = { id: null }): ScheduleExecutionFormGroup {
    const scheduleExecutionRawValue = {
      ...this.getFormDefaults(),
      ...scheduleExecution,
    };
    return new FormGroup<ScheduleExecutionFormGroupContent>({
      id: new FormControl(
        { value: scheduleExecutionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      createdAt: new FormControl(scheduleExecutionRawValue.createdAt),
      updatedAt: new FormControl(scheduleExecutionRawValue.updatedAt),
      schedule: new FormControl(scheduleExecutionRawValue.schedule),
    });
  }

  getScheduleExecution(form: ScheduleExecutionFormGroup): IScheduleExecution | NewScheduleExecution {
    return form.getRawValue() as IScheduleExecution | NewScheduleExecution;
  }

  resetForm(form: ScheduleExecutionFormGroup, scheduleExecution: ScheduleExecutionFormGroupInput): void {
    const scheduleExecutionRawValue = { ...this.getFormDefaults(), ...scheduleExecution };
    form.reset(
      {
        ...scheduleExecutionRawValue,
        id: { value: scheduleExecutionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ScheduleExecutionFormDefaults {
    return {
      id: null,
    };
  }
}
