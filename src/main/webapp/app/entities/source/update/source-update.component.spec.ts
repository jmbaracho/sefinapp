import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SourceFormService } from './source-form.service';
import { SourceService } from '../service/source.service';
import { ISource } from '../source.model';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { SourceUpdateComponent } from './source-update.component';

describe('Source Management Update Component', () => {
  let comp: SourceUpdateComponent;
  let fixture: ComponentFixture<SourceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sourceFormService: SourceFormService;
  let sourceService: SourceService;
  let agentService: AgentService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SourceUpdateComponent],
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
      .overrideTemplate(SourceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SourceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sourceFormService = TestBed.inject(SourceFormService);
    sourceService = TestBed.inject(SourceService);
    agentService = TestBed.inject(AgentService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Agent query and add missing value', () => {
      const source: ISource = { id: 456 };
      const agent: IAgent = { id: 34857 };
      source.agent = agent;

      const agentCollection: IAgent[] = [{ id: 58544 }];
      jest.spyOn(agentService, 'query').mockReturnValue(of(new HttpResponse({ body: agentCollection })));
      const additionalAgents = [agent];
      const expectedCollection: IAgent[] = [...additionalAgents, ...agentCollection];
      jest.spyOn(agentService, 'addAgentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ source });
      comp.ngOnInit();

      expect(agentService.query).toHaveBeenCalled();
      expect(agentService.addAgentToCollectionIfMissing).toHaveBeenCalledWith(
        agentCollection,
        ...additionalAgents.map(expect.objectContaining)
      );
      expect(comp.agentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const source: ISource = { id: 456 };
      const company: ICompany = { id: 29203 };
      source.company = company;

      const companyCollection: ICompany[] = [{ id: 75862 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ source });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const source: ISource = { id: 456 };
      const agent: IAgent = { id: 21223 };
      source.agent = agent;
      const company: ICompany = { id: 35649 };
      source.company = company;

      activatedRoute.data = of({ source });
      comp.ngOnInit();

      expect(comp.agentsSharedCollection).toContain(agent);
      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.source).toEqual(source);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISource>>();
      const source = { id: 123 };
      jest.spyOn(sourceFormService, 'getSource').mockReturnValue(source);
      jest.spyOn(sourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ source });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: source }));
      saveSubject.complete();

      // THEN
      expect(sourceFormService.getSource).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sourceService.update).toHaveBeenCalledWith(expect.objectContaining(source));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISource>>();
      const source = { id: 123 };
      jest.spyOn(sourceFormService, 'getSource').mockReturnValue({ id: null });
      jest.spyOn(sourceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ source: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: source }));
      saveSubject.complete();

      // THEN
      expect(sourceFormService.getSource).toHaveBeenCalled();
      expect(sourceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISource>>();
      const source = { id: 123 };
      jest.spyOn(sourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ source });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sourceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
