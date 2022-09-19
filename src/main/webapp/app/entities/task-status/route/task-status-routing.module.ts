import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaskStatusComponent } from '../list/task-status.component';
import { TaskStatusDetailComponent } from '../detail/task-status-detail.component';
import { TaskStatusUpdateComponent } from '../update/task-status-update.component';
import { TaskStatusRoutingResolveService } from './task-status-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const taskStatusRoute: Routes = [
  {
    path: '',
    component: TaskStatusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskStatusDetailComponent,
    resolve: {
      taskStatus: TaskStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskStatusUpdateComponent,
    resolve: {
      taskStatus: TaskStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskStatusUpdateComponent,
    resolve: {
      taskStatus: TaskStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taskStatusRoute)],
  exports: [RouterModule],
})
export class TaskStatusRoutingModule {}
