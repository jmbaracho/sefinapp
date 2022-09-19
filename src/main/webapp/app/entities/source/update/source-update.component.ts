import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SourceFormService, SourceFormGroup } from './source-form.service';
import { ISource } from '../source.model';
import { SourceService } from '../service/source.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-source-update',
  templateUrl: './source-update.component.html',
})
export class SourceUpdateComponent implements OnInit {
  isSaving = false;
  source: ISource | null = null;

  agentsSharedCollection: IAgent[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm: SourceFormGroup = this.sourceFormService.createSourceFormGroup();

  constructor(
    protected sourceService: SourceService,
    protected sourceFormService: SourceFormService,
    protected agentService: AgentService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAgent = (o1: IAgent | null, o2: IAgent | null): boolean => this.agentService.compareAgent(o1, o2);

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ source }) => {
      this.source = source;
      if (source) {
        this.updateForm(source);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const source = this.sourceFormService.getSource(this.editForm);
    if (source.id !== null) {
      this.subscribeToSaveResponse(this.sourceService.update(source));
    } else {
      this.subscribeToSaveResponse(this.sourceService.create(source));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISource>>): void {
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

  protected updateForm(source: ISource): void {
    this.source = source;
    this.sourceFormService.resetForm(this.editForm, source);

    this.agentsSharedCollection = this.agentService.addAgentToCollectionIfMissing<IAgent>(this.agentsSharedCollection, source.agent);
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      source.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.agentService
      .query()
      .pipe(map((res: HttpResponse<IAgent[]>) => res.body ?? []))
      .pipe(map((agents: IAgent[]) => this.agentService.addAgentToCollectionIfMissing<IAgent>(agents, this.source?.agent)))
      .subscribe((agents: IAgent[]) => (this.agentsSharedCollection = agents));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.source?.company)))
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
