import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RawDataComponent } from './list/raw-data.component';
import { RawDataDetailComponent } from './detail/raw-data-detail.component';
import { RawDataUpdateComponent } from './update/raw-data-update.component';
import { RawDataDeleteDialogComponent } from './delete/raw-data-delete-dialog.component';
import { RawDataRoutingModule } from './route/raw-data-routing.module';

@NgModule({
  imports: [SharedModule, RawDataRoutingModule],
  declarations: [RawDataComponent, RawDataDetailComponent, RawDataUpdateComponent, RawDataDeleteDialogComponent],
})
export class RawDataModule {}
