import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ScheduleExecutionComponent } from './list/schedule-execution.component';
import { ScheduleExecutionDetailComponent } from './detail/schedule-execution-detail.component';
import { ScheduleExecutionUpdateComponent } from './update/schedule-execution-update.component';
import { ScheduleExecutionDeleteDialogComponent } from './delete/schedule-execution-delete-dialog.component';
import { ScheduleExecutionRoutingModule } from './route/schedule-execution-routing.module';

@NgModule({
  imports: [SharedModule, ScheduleExecutionRoutingModule],
  declarations: [
    ScheduleExecutionComponent,
    ScheduleExecutionDetailComponent,
    ScheduleExecutionUpdateComponent,
    ScheduleExecutionDeleteDialogComponent,
  ],
})
export class ScheduleExecutionModule {}
