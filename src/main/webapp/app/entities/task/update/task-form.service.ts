import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITask, NewTask } from '../task.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITask for edit and NewTaskFormGroupInput for create.
 */
type TaskFormGroupInput = ITask | PartialWithRequiredKeyOf<NewTask>;

type TaskFormDefaults = Pick<NewTask, 'id'>;

type TaskFormGroupContent = {
  id: FormControl<ITask['id'] | NewTask['id']>;
  source: FormControl<ITask['source']>;
  attemptCount: FormControl<ITask['attemptCount']>;
  targetDateInterval: FormControl<ITask['targetDateInterval']>;
  createdAt: FormControl<ITask['createdAt']>;
  updatedAt: FormControl<ITask['updatedAt']>;
  status: FormControl<ITask['status']>;
  scheduleExecution: FormControl<ITask['scheduleExecution']>;
};

export type TaskFormGroup = FormGroup<TaskFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaskFormService {
  createTaskFormGroup(task: TaskFormGroupInput = { id: null }): TaskFormGroup {
    const taskRawValue = {
      ...this.getFormDefaults(),
      ...task,
    };
    return new FormGroup<TaskFormGroupContent>({
      id: new FormControl(
        { value: taskRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      source: new FormControl(taskRawValue.source),
      attemptCount: new FormControl(taskRawValue.attemptCount),
      targetDateInterval: new FormControl(taskRawValue.targetDateInterval),
      createdAt: new FormControl(taskRawValue.createdAt),
      updatedAt: new FormControl(taskRawValue.updatedAt),
      status: new FormControl(taskRawValue.status),
      scheduleExecution: new FormControl(taskRawValue.scheduleExecution),
    });
  }

  getTask(form: TaskFormGroup): ITask | NewTask {
    return form.getRawValue() as ITask | NewTask;
  }

  resetForm(form: TaskFormGroup, task: TaskFormGroupInput): void {
    const taskRawValue = { ...this.getFormDefaults(), ...task };
    form.reset(
      {
        ...taskRawValue,
        id: { value: taskRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TaskFormDefaults {
    return {
      id: null,
    };
  }
}
