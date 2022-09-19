import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AtividadeCPBSComponent } from './list/atividade-cpbs.component';
import { AtividadeCPBSDetailComponent } from './detail/atividade-cpbs-detail.component';
import { AtividadeCPBSUpdateComponent } from './update/atividade-cpbs-update.component';
import { AtividadeCPBSDeleteDialogComponent } from './delete/atividade-cpbs-delete-dialog.component';
import { AtividadeCPBSRoutingModule } from './route/atividade-cpbs-routing.module';

@NgModule({
  imports: [SharedModule, AtividadeCPBSRoutingModule],
  declarations: [AtividadeCPBSComponent, AtividadeCPBSDetailComponent, AtividadeCPBSUpdateComponent, AtividadeCPBSDeleteDialogComponent],
})
export class AtividadeCPBSModule {}
