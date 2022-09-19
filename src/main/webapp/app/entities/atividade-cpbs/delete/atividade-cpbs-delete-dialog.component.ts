import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAtividadeCPBS } from '../atividade-cpbs.model';
import { AtividadeCPBSService } from '../service/atividade-cpbs.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './atividade-cpbs-delete-dialog.component.html',
})
export class AtividadeCPBSDeleteDialogComponent {
  atividadeCPBS?: IAtividadeCPBS;

  constructor(protected atividadeCPBSService: AtividadeCPBSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.atividadeCPBSService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
