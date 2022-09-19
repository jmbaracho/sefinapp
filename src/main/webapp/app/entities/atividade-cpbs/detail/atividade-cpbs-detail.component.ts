import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAtividadeCPBS } from '../atividade-cpbs.model';

@Component({
  selector: 'jhi-atividade-cpbs-detail',
  templateUrl: './atividade-cpbs-detail.component.html',
})
export class AtividadeCPBSDetailComponent implements OnInit {
  atividadeCPBS: IAtividadeCPBS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atividadeCPBS }) => {
      this.atividadeCPBS = atividadeCPBS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
