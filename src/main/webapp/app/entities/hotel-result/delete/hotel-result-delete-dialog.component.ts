import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelResult } from '../hotel-result.model';
import { HotelResultService } from '../service/hotel-result.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './hotel-result-delete-dialog.component.html',
})
export class HotelResultDeleteDialogComponent {
  hotelResult?: IHotelResult;

  constructor(protected hotelResultService: HotelResultService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelResultService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
