import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HotelResultComponent } from './list/hotel-result.component';
import { HotelResultDetailComponent } from './detail/hotel-result-detail.component';
import { HotelResultUpdateComponent } from './update/hotel-result-update.component';
import { HotelResultDeleteDialogComponent } from './delete/hotel-result-delete-dialog.component';
import { HotelResultRoutingModule } from './route/hotel-result-routing.module';

@NgModule({
  imports: [SharedModule, HotelResultRoutingModule],
  declarations: [HotelResultComponent, HotelResultDetailComponent, HotelResultUpdateComponent, HotelResultDeleteDialogComponent],
})
export class HotelResultModule {}
