import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HotelResultFacilityComponent } from './list/hotel-result-facility.component';
import { HotelResultFacilityDetailComponent } from './detail/hotel-result-facility-detail.component';
import { HotelResultFacilityUpdateComponent } from './update/hotel-result-facility-update.component';
import { HotelResultFacilityDeleteDialogComponent } from './delete/hotel-result-facility-delete-dialog.component';
import { HotelResultFacilityRoutingModule } from './route/hotel-result-facility-routing.module';

@NgModule({
  imports: [SharedModule, HotelResultFacilityRoutingModule],
  declarations: [
    HotelResultFacilityComponent,
    HotelResultFacilityDetailComponent,
    HotelResultFacilityUpdateComponent,
    HotelResultFacilityDeleteDialogComponent,
  ],
})
export class HotelResultFacilityModule {}
