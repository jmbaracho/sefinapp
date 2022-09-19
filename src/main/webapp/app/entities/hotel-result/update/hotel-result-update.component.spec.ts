import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HotelResultFormService } from './hotel-result-form.service';
import { HotelResultService } from '../service/hotel-result.service';
import { IHotelResult } from '../hotel-result.model';
import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { HotelResultUpdateComponent } from './hotel-result-update.component';

describe('HotelResult Management Update Component', () => {
  let comp: HotelResultUpdateComponent;
  let fixture: ComponentFixture<HotelResultUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelResultFormService: HotelResultFormService;
  let hotelResultService: HotelResultService;
  let taskService: TaskService;
  let agentService: AgentService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HotelResultUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HotelResultUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelResultUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelResultFormService = TestBed.inject(HotelResultFormService);
    hotelResultService = TestBed.inject(HotelResultService);
    taskService = TestBed.inject(TaskService);
    agentService = TestBed.inject(AgentService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Task query and add missing value', () => {
      const hotelResult: IHotelResult = { id: 456 };
      const task: ITask = { id: 58485 };
      hotelResult.task = task;

      const taskCollection: ITask[] = [{ id: 10546 }];
      jest.spyOn(taskService, 'query').mockReturnValue(of(new HttpResponse({ body: taskCollection })));
      const additionalTasks = [task];
      const expectedCollection: ITask[] = [...additionalTasks, ...taskCollection];
      jest.spyOn(taskService, 'addTaskToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      expect(taskService.query).toHaveBeenCalled();
      expect(taskService.addTaskToCollectionIfMissing).toHaveBeenCalledWith(
        taskCollection,
        ...additionalTasks.map(expect.objectContaining)
      );
      expect(comp.tasksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Agent query and add missing value', () => {
      const hotelResult: IHotelResult = { id: 456 };
      const agent: IAgent = { id: 58894 };
      hotelResult.agent = agent;

      const agentCollection: IAgent[] = [{ id: 66057 }];
      jest.spyOn(agentService, 'query').mockReturnValue(of(new HttpResponse({ body: agentCollection })));
      const additionalAgents = [agent];
      const expectedCollection: IAgent[] = [...additionalAgents, ...agentCollection];
      jest.spyOn(agentService, 'addAgentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      expect(agentService.query).toHaveBeenCalled();
      expect(agentService.addAgentToCollectionIfMissing).toHaveBeenCalledWith(
        agentCollection,
        ...additionalAgents.map(expect.objectContaining)
      );
      expect(comp.agentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const hotelResult: IHotelResult = { id: 456 };
      const company: ICompany = { id: 13168 };
      hotelResult.company = company;

      const companyCollection: ICompany[] = [{ id: 97692 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hotelResult: IHotelResult = { id: 456 };
      const task: ITask = { id: 71726 };
      hotelResult.task = task;
      const agent: IAgent = { id: 75821 };
      hotelResult.agent = agent;
      const company: ICompany = { id: 68585 };
      hotelResult.company = company;

      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      expect(comp.tasksSharedCollection).toContain(task);
      expect(comp.agentsSharedCollection).toContain(agent);
      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.hotelResult).toEqual(hotelResult);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResult>>();
      const hotelResult = { id: 123 };
      jest.spyOn(hotelResultFormService, 'getHotelResult').mockReturnValue(hotelResult);
      jest.spyOn(hotelResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelResult }));
      saveSubject.complete();

      // THEN
      expect(hotelResultFormService.getHotelResult).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelResultService.update).toHaveBeenCalledWith(expect.objectContaining(hotelResult));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResult>>();
      const hotelResult = { id: 123 };
      jest.spyOn(hotelResultFormService, 'getHotelResult').mockReturnValue({ id: null });
      jest.spyOn(hotelResultService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResult: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelResult }));
      saveSubject.complete();

      // THEN
      expect(hotelResultFormService.getHotelResult).toHaveBeenCalled();
      expect(hotelResultService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResult>>();
      const hotelResult = { id: 123 };
      jest.spyOn(hotelResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelResultService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTask', () => {
      it('Should forward to taskService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(taskService, 'compareTask');
        comp.compareTask(entity, entity2);
        expect(taskService.compareTask).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAgent', () => {
      it('Should forward to agentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agentService, 'compareAgent');
        comp.compareAgent(entity, entity2);
        expect(agentService.compareAgent).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
