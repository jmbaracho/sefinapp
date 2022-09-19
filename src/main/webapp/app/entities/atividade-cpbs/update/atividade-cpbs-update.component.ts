import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AtividadeCPBSFormService, AtividadeCPBSFormGroup } from './atividade-cpbs-form.service';
import { IAtividadeCPBS } from '../atividade-cpbs.model';
import { AtividadeCPBSService } from '../service/atividade-cpbs.service';
import { IInscricaoCPBS } from 'app/entities/inscricao-cpbs/inscricao-cpbs.model';
import { InscricaoCPBSService } from 'app/entities/inscricao-cpbs/service/inscricao-cpbs.service';

@Component({
  selector: 'jhi-atividade-cpbs-update',
  templateUrl: './atividade-cpbs-update.component.html',
})
export class AtividadeCPBSUpdateComponent implements OnInit {
  isSaving = false;
  atividadeCPBS: IAtividadeCPBS | null = null;

  inscricaoCPBSSharedCollection: IInscricaoCPBS[] = [];

  editForm: AtividadeCPBSFormGroup = this.atividadeCPBSFormService.createAtividadeCPBSFormGroup();

  constructor(
    protected atividadeCPBSService: AtividadeCPBSService,
    protected atividadeCPBSFormService: AtividadeCPBSFormService,
    protected inscricaoCPBSService: InscricaoCPBSService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInscricaoCPBS = (o1: IInscricaoCPBS | null, o2: IInscricaoCPBS | null): boolean =>
    this.inscricaoCPBSService.compareInscricaoCPBS(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atividadeCPBS }) => {
      this.atividadeCPBS = atividadeCPBS;
      if (atividadeCPBS) {
        this.updateForm(atividadeCPBS);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const atividadeCPBS = this.atividadeCPBSFormService.getAtividadeCPBS(this.editForm);
    if (atividadeCPBS.id !== null) {
      this.subscribeToSaveResponse(this.atividadeCPBSService.update(atividadeCPBS));
    } else {
      this.subscribeToSaveResponse(this.atividadeCPBSService.create(atividadeCPBS));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtividadeCPBS>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(atividadeCPBS: IAtividadeCPBS): void {
    this.atividadeCPBS = atividadeCPBS;
    this.atividadeCPBSFormService.resetForm(this.editForm, atividadeCPBS);

    this.inscricaoCPBSSharedCollection = this.inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing<IInscricaoCPBS>(
      this.inscricaoCPBSSharedCollection,
      atividadeCPBS.inscricaoCpbs
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inscricaoCPBSService
      .query()
      .pipe(map((res: HttpResponse<IInscricaoCPBS[]>) => res.body ?? []))
      .pipe(
        map((inscricaoCPBS: IInscricaoCPBS[]) =>
          this.inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing<IInscricaoCPBS>(inscricaoCPBS, this.atividadeCPBS?.inscricaoCpbs)
        )
      )
      .subscribe((inscricaoCPBS: IInscricaoCPBS[]) => (this.inscricaoCPBSSharedCollection = inscricaoCPBS));
  }
}
