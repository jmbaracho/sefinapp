import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotelResultFacility } from '../hotel-result-facility.model';

@Component({
  selector: 'jhi-hotel-result-facility-detail',
  templateUrl: './hotel-result-facility-detail.component.html',
})
export class HotelResultFacilityDetailComponent implements OnInit {
  hotelResultFacility: IHotelResultFacility | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelResultFacility }) => {
      this.hotelResultFacility = hotelResultFacility;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
