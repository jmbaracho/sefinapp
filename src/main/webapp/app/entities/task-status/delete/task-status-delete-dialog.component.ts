import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskStatus } from '../task-status.model';
import { TaskStatusService } from '../service/task-status.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './task-status-delete-dialog.component.html',
})
export class TaskStatusDeleteDialogComponent {
  taskStatus?: ITaskStatus;

  constructor(protected taskStatusService: TaskStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskStatusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
