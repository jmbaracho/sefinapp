import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TaskStatusFormService, TaskStatusFormGroup } from './task-status-form.service';
import { ITaskStatus } from '../task-status.model';
import { TaskStatusService } from '../service/task-status.service';

@Component({
  selector: 'jhi-task-status-update',
  templateUrl: './task-status-update.component.html',
})
export class TaskStatusUpdateComponent implements OnInit {
  isSaving = false;
  taskStatus: ITaskStatus | null = null;

  editForm: TaskStatusFormGroup = this.taskStatusFormService.createTaskStatusFormGroup();

  constructor(
    protected taskStatusService: TaskStatusService,
    protected taskStatusFormService: TaskStatusFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskStatus }) => {
      this.taskStatus = taskStatus;
      if (taskStatus) {
        this.updateForm(taskStatus);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskStatus = this.taskStatusFormService.getTaskStatus(this.editForm);
    if (taskStatus.id !== null) {
      this.subscribeToSaveResponse(this.taskStatusService.update(taskStatus));
    } else {
      this.subscribeToSaveResponse(this.taskStatusService.create(taskStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskStatus>>): void {
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

  protected updateForm(taskStatus: ITaskStatus): void {
    this.taskStatus = taskStatus;
    this.taskStatusFormService.resetForm(this.editForm, taskStatus);
  }
}
