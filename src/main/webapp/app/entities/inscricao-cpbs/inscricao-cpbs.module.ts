import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InscricaoCPBSComponent } from './list/inscricao-cpbs.component';
import { InscricaoCPBSDetailComponent } from './detail/inscricao-cpbs-detail.component';
import { InscricaoCPBSUpdateComponent } from './update/inscricao-cpbs-update.component';
import { InscricaoCPBSDeleteDialogComponent } from './delete/inscricao-cpbs-delete-dialog.component';
import { InscricaoCPBSRoutingModule } from './route/inscricao-cpbs-routing.module';

@NgModule({
  imports: [SharedModule, InscricaoCPBSRoutingModule],
  declarations: [InscricaoCPBSComponent, InscricaoCPBSDetailComponent, InscricaoCPBSUpdateComponent, InscricaoCPBSDeleteDialogComponent],
})
export class InscricaoCPBSModule {}
