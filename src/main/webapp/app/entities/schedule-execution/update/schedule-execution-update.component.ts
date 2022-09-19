import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ScheduleExecutionFormService, ScheduleExecutionFormGroup } from './schedule-execution-form.service';
import { IScheduleExecution } from '../schedule-execution.model';
import { ScheduleExecutionService } from '../service/schedule-execution.service';
import { ISchedule } from 'app/entities/schedule/schedule.model';
import { ScheduleService } from 'app/entities/schedule/service/schedule.service';

@Component({
  selector: 'jhi-schedule-execution-update',
  templateUrl: './schedule-execution-update.component.html',
})
export class ScheduleExecutionUpdateComponent implements OnInit {
  isSaving = false;
  scheduleExecution: IScheduleExecution | null = null;

  schedulesSharedCollection: ISchedule[] = [];

  editForm: ScheduleExecutionFormGroup = this.scheduleExecutionFormService.createScheduleExecutionFormGroup();

  constructor(
    protected scheduleExecutionService: ScheduleExecutionService,
    protected scheduleExecutionFormService: ScheduleExecutionFormService,
    protected scheduleService: ScheduleService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSchedule = (o1: ISchedule | null, o2: ISchedule | null): boolean => this.scheduleService.compareSchedule(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleExecution }) => {
      this.scheduleExecution = scheduleExecution;
      if (scheduleExecution) {
        this.updateForm(scheduleExecution);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheduleExecution = this.scheduleExecutionFormService.getScheduleExecution(this.editForm);
    if (scheduleExecution.id !== null) {
      this.subscribeToSaveResponse(this.scheduleExecutionService.update(scheduleExecution));
    } else {
      this.subscribeToSaveResponse(this.scheduleExecutionService.create(scheduleExecution));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleExecution>>): void {
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

  protected updateForm(scheduleExecution: IScheduleExecution): void {
    this.scheduleExecution = scheduleExecution;
    this.scheduleExecutionFormService.resetForm(this.editForm, scheduleExecution);

    this.schedulesSharedCollection = this.scheduleService.addScheduleToCollectionIfMissing<ISchedule>(
      this.schedulesSharedCollection,
      scheduleExecution.schedule
    );
  }

  protected loadRelationshipsOptions(): void {
    this.scheduleService
      .query()
      .pipe(map((res: HttpResponse<ISchedule[]>) => res.body ?? []))
      .pipe(
        map((schedules: ISchedule[]) =>
          this.scheduleService.addScheduleToCollectionIfMissing<ISchedule>(schedules, this.scheduleExecution?.schedule)
        )
      )
      .subscribe((schedules: ISchedule[]) => (this.schedulesSharedCollection = schedules));
  }
}
