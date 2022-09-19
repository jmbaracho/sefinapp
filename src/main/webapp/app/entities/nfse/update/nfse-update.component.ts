import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NfseFormService, NfseFormGroup } from './nfse-form.service';
import { INfse } from '../nfse.model';
import { NfseService } from '../service/nfse.service';
import { IInscricaoCPBS } from 'app/entities/inscricao-cpbs/inscricao-cpbs.model';
import { InscricaoCPBSService } from 'app/entities/inscricao-cpbs/service/inscricao-cpbs.service';

@Component({
  selector: 'jhi-nfse-update',
  templateUrl: './nfse-update.component.html',
})
export class NfseUpdateComponent implements OnInit {
  isSaving = false;
  nfse: INfse | null = null;

  inscricaoCPBSSharedCollection: IInscricaoCPBS[] = [];

  editForm: NfseFormGroup = this.nfseFormService.createNfseFormGroup();

  constructor(
    protected nfseService: NfseService,
    protected nfseFormService: NfseFormService,
    protected inscricaoCPBSService: InscricaoCPBSService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInscricaoCPBS = (o1: IInscricaoCPBS | null, o2: IInscricaoCPBS | null): boolean =>
    this.inscricaoCPBSService.compareInscricaoCPBS(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nfse }) => {
      this.nfse = nfse;
      if (nfse) {
        this.updateForm(nfse);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nfse = this.nfseFormService.getNfse(this.editForm);
    if (nfse.id !== null) {
      this.subscribeToSaveResponse(this.nfseService.update(nfse));
    } else {
      this.subscribeToSaveResponse(this.nfseService.create(nfse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INfse>>): void {
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

  protected updateForm(nfse: INfse): void {
    this.nfse = nfse;
    this.nfseFormService.resetForm(this.editForm, nfse);

    this.inscricaoCPBSSharedCollection = this.inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing<IInscricaoCPBS>(
      this.inscricaoCPBSSharedCollection,
      nfse.inscricaoCpbs
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inscricaoCPBSService
      .query()
      .pipe(map((res: HttpResponse<IInscricaoCPBS[]>) => res.body ?? []))
      .pipe(
        map((inscricaoCPBS: IInscricaoCPBS[]) =>
          this.inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing<IInscricaoCPBS>(inscricaoCPBS, this.nfse?.inscricaoCpbs)
        )
      )
      .subscribe((inscricaoCPBS: IInscricaoCPBS[]) => (this.inscricaoCPBSSharedCollection = inscricaoCPBS));
  }
}
