import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { InscricaoCPBSFormService, InscricaoCPBSFormGroup } from './inscricao-cpbs-form.service';
import { IInscricaoCPBS } from '../inscricao-cpbs.model';
import { InscricaoCPBSService } from '../service/inscricao-cpbs.service';
import { CPBSSituacaoEnum } from 'app/entities/enumerations/cpbs-situacao-enum.model';

@Component({
  selector: 'jhi-inscricao-cpbs-update',
  templateUrl: './inscricao-cpbs-update.component.html',
})
export class InscricaoCPBSUpdateComponent implements OnInit {
  isSaving = false;
  inscricaoCPBS: IInscricaoCPBS | null = null;
  cPBSSituacaoEnumValues = Object.keys(CPBSSituacaoEnum);

  editForm: InscricaoCPBSFormGroup = this.inscricaoCPBSFormService.createInscricaoCPBSFormGroup();

  constructor(
    protected inscricaoCPBSService: InscricaoCPBSService,
    protected inscricaoCPBSFormService: InscricaoCPBSFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscricaoCPBS }) => {
      this.inscricaoCPBS = inscricaoCPBS;
      if (inscricaoCPBS) {
        this.updateForm(inscricaoCPBS);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscricaoCPBS = this.inscricaoCPBSFormService.getInscricaoCPBS(this.editForm);
    if (inscricaoCPBS.id !== null) {
      this.subscribeToSaveResponse(this.inscricaoCPBSService.update(inscricaoCPBS));
    } else {
      this.subscribeToSaveResponse(this.inscricaoCPBSService.create(inscricaoCPBS));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscricaoCPBS>>): void {
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

  protected updateForm(inscricaoCPBS: IInscricaoCPBS): void {
    this.inscricaoCPBS = inscricaoCPBS;
    this.inscricaoCPBSFormService.resetForm(this.editForm, inscricaoCPBS);
  }
}
