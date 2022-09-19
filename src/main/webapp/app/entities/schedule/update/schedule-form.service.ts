import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISchedule, NewSchedule } from '../schedule.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISchedule for edit and NewScheduleFormGroupInput for create.
 */
type ScheduleFormGroupInput = ISchedule | PartialWithRequiredKeyOf<NewSchedule>;

type ScheduleFormDefaults = Pick<NewSchedule, 'id'>;

type ScheduleFormGroupContent = {
  id: FormControl<ISchedule['id'] | NewSchedule['id']>;
  rule: FormControl<ISchedule['rule']>;
  firstInterval: FormControl<ISchedule['firstInterval']>;
  secondInterval: FormControl<ISchedule['secondInterval']>;
  thirdInterval: FormControl<ISchedule['thirdInterval']>;
  createdAt: FormControl<ISchedule['createdAt']>;
  updatedAt: FormControl<ISchedule['updatedAt']>;
  agent: FormControl<ISchedule['agent']>;
};

export type ScheduleFormGroup = FormGroup<ScheduleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ScheduleFormService {
  createScheduleFormGroup(schedule: ScheduleFormGroupInput = { id: null }): ScheduleFormGroup {
    const scheduleRawValue = {
      ...this.getFormDefaults(),
      ...schedule,
    };
    return new FormGroup<ScheduleFormGroupContent>({
      id: new FormControl(
        { value: scheduleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      rule: new FormControl(scheduleRawValue.rule),
      firstInterval: new FormControl(scheduleRawValue.firstInterval),
      secondInterval: new FormControl(scheduleRawValue.secondInterval),
      thirdInterval: new FormControl(scheduleRawValue.thirdInterval),
      createdAt: new FormControl(scheduleRawValue.createdAt),
      updatedAt: new FormControl(scheduleRawValue.updatedAt),
      agent: new FormControl(scheduleRawValue.agent),
    });
  }

  getSchedule(form: ScheduleFormGroup): ISchedule | NewSchedule {
    return form.getRawValue() as ISchedule | NewSchedule;
  }

  resetForm(form: ScheduleFormGroup, schedule: ScheduleFormGroupInput): void {
    const scheduleRawValue = { ...this.getFormDefaults(), ...schedule };
    form.reset(
      {
        ...scheduleRawValue,
        id: { value: scheduleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ScheduleFormDefaults {
    return {
      id: null,
    };
  }
}
