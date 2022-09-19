import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleExecution } from '../schedule-execution.model';

@Component({
  selector: 'jhi-schedule-execution-detail',
  templateUrl: './schedule-execution-detail.component.html',
})
export class ScheduleExecutionDetailComponent implements OnInit {
  scheduleExecution: IScheduleExecution | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleExecution }) => {
      this.scheduleExecution = scheduleExecution;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
