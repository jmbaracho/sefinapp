import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TaskStatusComponent } from './list/task-status.component';
import { TaskStatusDetailComponent } from './detail/task-status-detail.component';
import { TaskStatusUpdateComponent } from './update/task-status-update.component';
import { TaskStatusDeleteDialogComponent } from './delete/task-status-delete-dialog.component';
import { TaskStatusRoutingModule } from './route/task-status-routing.module';

@NgModule({
  imports: [SharedModule, TaskStatusRoutingModule],
  declarations: [TaskStatusComponent, TaskStatusDetailComponent, TaskStatusUpdateComponent, TaskStatusDeleteDialogComponent],
})
export class TaskStatusModule {}
