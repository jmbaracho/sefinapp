import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelResultFacility } from '../hotel-result-facility.model';
import { HotelResultFacilityService } from '../service/hotel-result-facility.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './hotel-result-facility-delete-dialog.component.html',
})
export class HotelResultFacilityDeleteDialogComponent {
  hotelResultFacility?: IHotelResultFacility;

  constructor(protected hotelResultFacilityService: HotelResultFacilityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelResultFacilityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
