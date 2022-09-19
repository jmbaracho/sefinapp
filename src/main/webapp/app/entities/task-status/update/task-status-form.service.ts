import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITaskStatus, NewTaskStatus } from '../task-status.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaskStatus for edit and NewTaskStatusFormGroupInput for create.
 */
type TaskStatusFormGroupInput = ITaskStatus | PartialWithRequiredKeyOf<NewTaskStatus>;

type TaskStatusFormDefaults = Pick<NewTaskStatus, 'id'>;

type TaskStatusFormGroupContent = {
  id: FormControl<ITaskStatus['id'] | NewTaskStatus['id']>;
  name: FormControl<ITaskStatus['name']>;
  createdAt: FormControl<ITaskStatus['createdAt']>;
  updatedAt: FormControl<ITaskStatus['updatedAt']>;
};

export type TaskStatusFormGroup = FormGroup<TaskStatusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaskStatusFormService {
  createTaskStatusFormGroup(taskStatus: TaskStatusFormGroupInput = { id: null }): TaskStatusFormGroup {
    const taskStatusRawValue = {
      ...this.getFormDefaults(),
      ...taskStatus,
    };
    return new FormGroup<TaskStatusFormGroupContent>({
      id: new FormControl(
        { value: taskStatusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(taskStatusRawValue.name),
      createdAt: new FormControl(taskStatusRawValue.createdAt),
      updatedAt: new FormControl(taskStatusRawValue.updatedAt),
    });
  }

  getTaskStatus(form: TaskStatusFormGroup): ITaskStatus | NewTaskStatus {
    return form.getRawValue() as ITaskStatus | NewTaskStatus;
  }

  resetForm(form: TaskStatusFormGroup, taskStatus: TaskStatusFormGroupInput): void {
    const taskStatusRawValue = { ...this.getFormDefaults(), ...taskStatus };
    form.reset(
      {
        ...taskStatusRawValue,
        id: { value: taskStatusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TaskStatusFormDefaults {
    return {
      id: null,
    };
  }
}
