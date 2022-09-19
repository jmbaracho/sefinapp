import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NfseComponent } from './list/nfse.component';
import { NfseDetailComponent } from './detail/nfse-detail.component';
import { NfseUpdateComponent } from './update/nfse-update.component';
import { NfseDeleteDialogComponent } from './delete/nfse-delete-dialog.component';
import { NfseRoutingModule } from './route/nfse-routing.module';

@NgModule({
  imports: [SharedModule, NfseRoutingModule],
  declarations: [NfseComponent, NfseDetailComponent, NfseUpdateComponent, NfseDeleteDialogComponent],
})
export class NfseModule {}
