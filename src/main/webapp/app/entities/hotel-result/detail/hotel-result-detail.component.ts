import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotelResult } from '../hotel-result.model';

@Component({
  selector: 'jhi-hotel-result-detail',
  templateUrl: './hotel-result-detail.component.html',
})
export class HotelResultDetailComponent implements OnInit {
  hotelResult: IHotelResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelResult }) => {
      this.hotelResult = hotelResult;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
