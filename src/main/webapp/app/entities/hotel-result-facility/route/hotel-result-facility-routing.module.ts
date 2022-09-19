import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HotelResultFacilityComponent } from '../list/hotel-result-facility.component';
import { HotelResultFacilityDetailComponent } from '../detail/hotel-result-facility-detail.component';
import { HotelResultFacilityUpdateComponent } from '../update/hotel-result-facility-update.component';
import { HotelResultFacilityRoutingResolveService } from './hotel-result-facility-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const hotelResultFacilityRoute: Routes = [
  {
    path: '',
    component: HotelResultFacilityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelResultFacilityDetailComponent,
    resolve: {
      hotelResultFacility: HotelResultFacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelResultFacilityUpdateComponent,
    resolve: {
      hotelResultFacility: HotelResultFacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelResultFacilityUpdateComponent,
    resolve: {
      hotelResultFacility: HotelResultFacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hotelResultFacilityRoute)],
  exports: [RouterModule],
})
export class HotelResultFacilityRoutingModule {}
