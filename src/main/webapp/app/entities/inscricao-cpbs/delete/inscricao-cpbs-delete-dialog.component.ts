import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInscricaoCPBS } from '../inscricao-cpbs.model';
import { InscricaoCPBSService } from '../service/inscricao-cpbs.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './inscricao-cpbs-delete-dialog.component.html',
})
export class InscricaoCPBSDeleteDialogComponent {
  inscricaoCPBS?: IInscricaoCPBS;

  constructor(protected inscricaoCPBSService: InscricaoCPBSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inscricaoCPBSService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
