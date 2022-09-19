import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TagTextComponent } from './list/tag-text.component';
import { TagTextDetailComponent } from './detail/tag-text-detail.component';
import { TagTextUpdateComponent } from './update/tag-text-update.component';
import { TagTextDeleteDialogComponent } from './delete/tag-text-delete-dialog.component';
import { TagTextRoutingModule } from './route/tag-text-routing.module';

@NgModule({
  imports: [SharedModule, TagTextRoutingModule],
  declarations: [TagTextComponent, TagTextDetailComponent, TagTextUpdateComponent, TagTextDeleteDialogComponent],
})
export class TagTextModule {}
