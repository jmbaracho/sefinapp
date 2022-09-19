import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AtividadeCPBSComponent } from '../list/atividade-cpbs.component';
import { AtividadeCPBSDetailComponent } from '../detail/atividade-cpbs-detail.component';
import { AtividadeCPBSUpdateComponent } from '../update/atividade-cpbs-update.component';
import { AtividadeCPBSRoutingResolveService } from './atividade-cpbs-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const atividadeCPBSRoute: Routes = [
  {
    path: '',
    component: AtividadeCPBSComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AtividadeCPBSDetailComponent,
    resolve: {
      atividadeCPBS: AtividadeCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AtividadeCPBSUpdateComponent,
    resolve: {
      atividadeCPBS: AtividadeCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AtividadeCPBSUpdateComponent,
    resolve: {
      atividadeCPBS: AtividadeCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(atividadeCPBSRoute)],
  exports: [RouterModule],
})
export class AtividadeCPBSRoutingModule {}
