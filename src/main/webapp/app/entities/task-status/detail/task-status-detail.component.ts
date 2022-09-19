import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskStatus } from '../task-status.model';

@Component({
  selector: 'jhi-task-status-detail',
  templateUrl: './task-status-detail.component.html',
})
export class TaskStatusDetailComponent implements OnInit {
  taskStatus: ITaskStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskStatus }) => {
      this.taskStatus = taskStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
