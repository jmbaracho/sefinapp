import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ScheduleExecutionComponent } from '../list/schedule-execution.component';
import { ScheduleExecutionDetailComponent } from '../detail/schedule-execution-detail.component';
import { ScheduleExecutionUpdateComponent } from '../update/schedule-execution-update.component';
import { ScheduleExecutionRoutingResolveService } from './schedule-execution-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const scheduleExecutionRoute: Routes = [
  {
    path: '',
    component: ScheduleExecutionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScheduleExecutionDetailComponent,
    resolve: {
      scheduleExecution: ScheduleExecutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScheduleExecutionUpdateComponent,
    resolve: {
      scheduleExecution: ScheduleExecutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScheduleExecutionUpdateComponent,
    resolve: {
      scheduleExecution: ScheduleExecutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(scheduleExecutionRoute)],
  exports: [RouterModule],
})
export class ScheduleExecutionRoutingModule {}
