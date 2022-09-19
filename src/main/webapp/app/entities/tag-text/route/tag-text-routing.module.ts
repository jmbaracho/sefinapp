import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TagTextComponent } from '../list/tag-text.component';
import { TagTextDetailComponent } from '../detail/tag-text-detail.component';
import { TagTextUpdateComponent } from '../update/tag-text-update.component';
import { TagTextRoutingResolveService } from './tag-text-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tagTextRoute: Routes = [
  {
    path: '',
    component: TagTextComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TagTextDetailComponent,
    resolve: {
      tagText: TagTextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TagTextUpdateComponent,
    resolve: {
      tagText: TagTextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TagTextUpdateComponent,
    resolve: {
      tagText: TagTextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tagTextRoute)],
  exports: [RouterModule],
})
export class TagTextRoutingModule {}
