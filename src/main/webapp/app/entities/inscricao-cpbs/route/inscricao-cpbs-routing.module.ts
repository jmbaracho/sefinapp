import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InscricaoCPBSComponent } from '../list/inscricao-cpbs.component';
import { InscricaoCPBSDetailComponent } from '../detail/inscricao-cpbs-detail.component';
import { InscricaoCPBSUpdateComponent } from '../update/inscricao-cpbs-update.component';
import { InscricaoCPBSRoutingResolveService } from './inscricao-cpbs-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const inscricaoCPBSRoute: Routes = [
  {
    path: '',
    component: InscricaoCPBSComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InscricaoCPBSDetailComponent,
    resolve: {
      inscricaoCPBS: InscricaoCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InscricaoCPBSUpdateComponent,
    resolve: {
      inscricaoCPBS: InscricaoCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InscricaoCPBSUpdateComponent,
    resolve: {
      inscricaoCPBS: InscricaoCPBSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inscricaoCPBSRoute)],
  exports: [RouterModule],
})
export class InscricaoCPBSRoutingModule {}
