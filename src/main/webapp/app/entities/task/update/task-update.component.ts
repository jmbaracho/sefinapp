import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TaskFormService, TaskFormGroup } from './task-form.service';
import { ITask } from '../task.model';
import { TaskService } from '../service/task.service';
import { ITaskStatus } from 'app/entities/task-status/task-status.model';
import { TaskStatusService } from 'app/entities/task-status/service/task-status.service';
import { IScheduleExecution } from 'app/entities/schedule-execution/schedule-execution.model';
import { ScheduleExecutionService } from 'app/entities/schedule-execution/service/schedule-execution.service';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  task: ITask | null = null;

  taskStatusesSharedCollection: ITaskStatus[] = [];
  scheduleExecutionsSharedCollection: IScheduleExecution[] = [];

  editForm: TaskFormGroup = this.taskFormService.createTaskFormGroup();

  constructor(
    protected taskService: TaskService,
    protected taskFormService: TaskFormService,
    protected taskStatusService: TaskStatusService,
    protected scheduleExecutionService: ScheduleExecutionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTaskStatus = (o1: ITaskStatus | null, o2: ITaskStatus | null): boolean => this.taskStatusService.compareTaskStatus(o1, o2);

  compareScheduleExecution = (o1: IScheduleExecution | null, o2: IScheduleExecution | null): boolean =>
    this.scheduleExecutionService.compareScheduleExecution(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      this.task = task;
      if (task) {
        this.updateForm(task);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.taskFormService.getTask(this.editForm);
    if (task.id !== null) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(task: ITask): void {
    this.task = task;
    this.taskFormService.resetForm(this.editForm, task);

    this.taskStatusesSharedCollection = this.taskStatusService.addTaskStatusToCollectionIfMissing<ITaskStatus>(
      this.taskStatusesSharedCollection,
      task.status
    );
    this.scheduleExecutionsSharedCollection = this.scheduleExecutionService.addScheduleExecutionToCollectionIfMissing<IScheduleExecution>(
      this.scheduleExecutionsSharedCollection,
      task.scheduleExecution
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taskStatusService
      .query()
      .pipe(map((res: HttpResponse<ITaskStatus[]>) => res.body ?? []))
      .pipe(
        map((taskStatuses: ITaskStatus[]) =>
          this.taskStatusService.addTaskStatusToCollectionIfMissing<ITaskStatus>(taskStatuses, this.task?.status)
        )
      )
      .subscribe((taskStatuses: ITaskStatus[]) => (this.taskStatusesSharedCollection = taskStatuses));

    this.scheduleExecutionService
      .query()
      .pipe(map((res: HttpResponse<IScheduleExecution[]>) => res.body ?? []))
      .pipe(
        map((scheduleExecutions: IScheduleExecution[]) =>
          this.scheduleExecutionService.addScheduleExecutionToCollectionIfMissing<IScheduleExecution>(
            scheduleExecutions,
            this.task?.scheduleExecution
          )
        )
      )
      .subscribe((scheduleExecutions: IScheduleExecution[]) => (this.scheduleExecutionsSharedCollection = scheduleExecutions));
  }
}
