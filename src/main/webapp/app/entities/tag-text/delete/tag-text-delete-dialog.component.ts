import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITagText } from '../tag-text.model';
import { TagTextService } from '../service/tag-text.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tag-text-delete-dialog.component.html',
})
export class TagTextDeleteDialogComponent {
  tagText?: ITagText;

  constructor(protected tagTextService: TagTextService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tagTextService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
