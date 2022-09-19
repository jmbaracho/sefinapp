import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RawDataFormService, RawDataFormGroup } from './raw-data-form.service';
import { IRawData } from '../raw-data.model';
import { RawDataService } from '../service/raw-data.service';
import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';

@Component({
  selector: 'jhi-raw-data-update',
  templateUrl: './raw-data-update.component.html',
})
export class RawDataUpdateComponent implements OnInit {
  isSaving = false;
  rawData: IRawData | null = null;

  tasksSharedCollection: ITask[] = [];

  editForm: RawDataFormGroup = this.rawDataFormService.createRawDataFormGroup();

  constructor(
    protected rawDataService: RawDataService,
    protected rawDataFormService: RawDataFormService,
    protected taskService: TaskService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTask = (o1: ITask | null, o2: ITask | null): boolean => this.taskService.compareTask(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawData }) => {
      this.rawData = rawData;
      if (rawData) {
        this.updateForm(rawData);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rawData = this.rawDataFormService.getRawData(this.editForm);
    if (rawData.id !== null) {
      this.subscribeToSaveResponse(this.rawDataService.update(rawData));
    } else {
      this.subscribeToSaveResponse(this.rawDataService.create(rawData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRawData>>): void {
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

  protected updateForm(rawData: IRawData): void {
    this.rawData = rawData;
    this.rawDataFormService.resetForm(this.editForm, rawData);

    this.tasksSharedCollection = this.taskService.addTaskToCollectionIfMissing<ITask>(this.tasksSharedCollection, rawData.task);
  }

  protected loadRelationshipsOptions(): void {
    this.taskService
      .query()
      .pipe(map((res: HttpResponse<ITask[]>) => res.body ?? []))
      .pipe(map((tasks: ITask[]) => this.taskService.addTaskToCollectionIfMissing<ITask>(tasks, this.rawData?.task)))
      .subscribe((tasks: ITask[]) => (this.tasksSharedCollection = tasks));
  }
}
