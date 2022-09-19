import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRawData } from '../raw-data.model';

@Component({
  selector: 'jhi-raw-data-detail',
  templateUrl: './raw-data-detail.component.html',
})
export class RawDataDetailComponent implements OnInit {
  rawData: IRawData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawData }) => {
      this.rawData = rawData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
