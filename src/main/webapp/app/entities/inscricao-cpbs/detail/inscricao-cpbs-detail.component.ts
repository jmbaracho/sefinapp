import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInscricaoCPBS } from '../inscricao-cpbs.model';

@Component({
  selector: 'jhi-inscricao-cpbs-detail',
  templateUrl: './inscricao-cpbs-detail.component.html',
})
export class InscricaoCPBSDetailComponent implements OnInit {
  inscricaoCPBS: IInscricaoCPBS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscricaoCPBS }) => {
      this.inscricaoCPBS = inscricaoCPBS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
