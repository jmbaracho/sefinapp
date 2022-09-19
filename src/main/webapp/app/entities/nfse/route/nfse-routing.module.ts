import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NfseComponent } from '../list/nfse.component';
import { NfseDetailComponent } from '../detail/nfse-detail.component';
import { NfseUpdateComponent } from '../update/nfse-update.component';
import { NfseRoutingResolveService } from './nfse-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nfseRoute: Routes = [
  {
    path: '',
    component: NfseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NfseDetailComponent,
    resolve: {
      nfse: NfseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NfseUpdateComponent,
    resolve: {
      nfse: NfseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NfseUpdateComponent,
    resolve: {
      nfse: NfseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nfseRoute)],
  exports: [RouterModule],
})
export class NfseRoutingModule {}
