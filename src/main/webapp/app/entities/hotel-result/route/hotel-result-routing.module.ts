import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HotelResultComponent } from '../list/hotel-result.component';
import { HotelResultDetailComponent } from '../detail/hotel-result-detail.component';
import { HotelResultUpdateComponent } from '../update/hotel-result-update.component';
import { HotelResultRoutingResolveService } from './hotel-result-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const hotelResultRoute: Routes = [
  {
    path: '',
    component: HotelResultComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelResultDetailComponent,
    resolve: {
      hotelResult: HotelResultRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelResultUpdateComponent,
    resolve: {
      hotelResult: HotelResultRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelResultUpdateComponent,
    resolve: {
      hotelResult: HotelResultRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hotelResultRoute)],
  exports: [RouterModule],
})
export class HotelResultRoutingModule {}
