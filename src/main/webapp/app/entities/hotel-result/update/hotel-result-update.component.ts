import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HotelResultFormService, HotelResultFormGroup } from './hotel-result-form.service';
import { IHotelResult } from '../hotel-result.model';
import { HotelResultService } from '../service/hotel-result.service';
import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-hotel-result-update',
  templateUrl: './hotel-result-update.component.html',
})
export class HotelResultUpdateComponent implements OnInit {
  isSaving = false;
  hotelResult: IHotelResult | null = null;

  tasksSharedCollection: ITask[] = [];
  agentsSharedCollection: IAgent[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm: HotelResultFormGroup = this.hotelResultFormService.createHotelResultFormGroup();

  constructor(
    protected hotelResultService: HotelResultService,
    protected hotelResultFormService: HotelResultFormService,
    protected taskService: TaskService,
    protected agentService: AgentService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTask = (o1: ITask | null, o2: ITask | null): boolean => this.taskService.compareTask(o1, o2);

  compareAgent = (o1: IAgent | null, o2: IAgent | null): boolean => this.agentService.compareAgent(o1, o2);

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelResult }) => {
      this.hotelResult = hotelResult;
      if (hotelResult) {
        this.updateForm(hotelResult);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelResult = this.hotelResultFormService.getHotelResult(this.editForm);
    if (hotelResult.id !== null) {
      this.subscribeToSaveResponse(this.hotelResultService.update(hotelResult));
    } else {
      this.subscribeToSaveResponse(this.hotelResultService.create(hotelResult));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelResult>>): void {
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

  protected updateForm(hotelResult: IHotelResult): void {
    this.hotelResult = hotelResult;
    this.hotelResultFormService.resetForm(this.editForm, hotelResult);

    this.tasksSharedCollection = this.taskService.addTaskToCollectionIfMissing<ITask>(this.tasksSharedCollection, hotelResult.task);
    this.agentsSharedCollection = this.agentService.addAgentToCollectionIfMissing<IAgent>(this.agentsSharedCollection, hotelResult.agent);
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      hotelResult.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taskService
      .query()
      .pipe(map((res: HttpResponse<ITask[]>) => res.body ?? []))
      .pipe(map((tasks: ITask[]) => this.taskService.addTaskToCollectionIfMissing<ITask>(tasks, this.hotelResult?.task)))
      .subscribe((tasks: ITask[]) => (this.tasksSharedCollection = tasks));

    this.agentService
      .query()
      .pipe(map((res: HttpResponse<IAgent[]>) => res.body ?? []))
      .pipe(map((agents: IAgent[]) => this.agentService.addAgentToCollectionIfMissing<IAgent>(agents, this.hotelResult?.agent)))
      .subscribe((agents: IAgent[]) => (this.agentsSharedCollection = agents));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.hotelResult?.company))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
