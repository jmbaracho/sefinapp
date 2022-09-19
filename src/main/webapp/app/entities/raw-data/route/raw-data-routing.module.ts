import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RawDataComponent } from '../list/raw-data.component';
import { RawDataDetailComponent } from '../detail/raw-data-detail.component';
import { RawDataUpdateComponent } from '../update/raw-data-update.component';
import { RawDataRoutingResolveService } from './raw-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const rawDataRoute: Routes = [
  {
    path: '',
    component: RawDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RawDataDetailComponent,
    resolve: {
      rawData: RawDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RawDataUpdateComponent,
    resolve: {
      rawData: RawDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RawDataUpdateComponent,
    resolve: {
      rawData: RawDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rawDataRoute)],
  exports: [RouterModule],
})
export class RawDataRoutingModule {}
