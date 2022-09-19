import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IScheduleExecution } from '../schedule-execution.model';
import { ScheduleExecutionService } from '../service/schedule-execution.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './schedule-execution-delete-dialog.component.html',
})
export class ScheduleExecutionDeleteDialogComponent {
  scheduleExecution?: IScheduleExecution;

  constructor(protected scheduleExecutionService: ScheduleExecutionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scheduleExecutionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
