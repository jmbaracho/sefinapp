import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INfse } from '../nfse.model';

@Component({
  selector: 'jhi-nfse-detail',
  templateUrl: './nfse-detail.component.html',
})
export class NfseDetailComponent implements OnInit {
  nfse: INfse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nfse }) => {
      this.nfse = nfse;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
