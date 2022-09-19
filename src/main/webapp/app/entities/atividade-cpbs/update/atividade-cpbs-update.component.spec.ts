import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AtividadeCPBSFormService } from './atividade-cpbs-form.service';
import { AtividadeCPBSService } from '../service/atividade-cpbs.service';
import { IAtividadeCPBS } from '../atividade-cpbs.model';
import { IInscricaoCPBS } from 'app/entities/inscricao-cpbs/inscricao-cpbs.model';
import { InscricaoCPBSService } from 'app/entities/inscricao-cpbs/service/inscricao-cpbs.service';

import { AtividadeCPBSUpdateComponent } from './atividade-cpbs-update.component';

describe('AtividadeCPBS Management Update Component', () => {
  let comp: AtividadeCPBSUpdateComponent;
  let fixture: ComponentFixture<AtividadeCPBSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atividadeCPBSFormService: AtividadeCPBSFormService;
  let atividadeCPBSService: AtividadeCPBSService;
  let inscricaoCPBSService: InscricaoCPBSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AtividadeCPBSUpdateComponent],
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
      .overrideTemplate(AtividadeCPBSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtividadeCPBSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atividadeCPBSFormService = TestBed.inject(AtividadeCPBSFormService);
    atividadeCPBSService = TestBed.inject(AtividadeCPBSService);
    inscricaoCPBSService = TestBed.inject(InscricaoCPBSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InscricaoCPBS query and add missing value', () => {
      const atividadeCPBS: IAtividadeCPBS = { id: 456 };
      const inscricaoCpbs: IInscricaoCPBS = { id: 14494 };
      atividadeCPBS.inscricaoCpbs = inscricaoCpbs;

      const inscricaoCPBSCollection: IInscricaoCPBS[] = [{ id: 31152 }];
      jest.spyOn(inscricaoCPBSService, 'query').mockReturnValue(of(new HttpResponse({ body: inscricaoCPBSCollection })));
      const additionalInscricaoCPBS = [inscricaoCpbs];
      const expectedCollection: IInscricaoCPBS[] = [...additionalInscricaoCPBS, ...inscricaoCPBSCollection];
      jest.spyOn(inscricaoCPBSService, 'addInscricaoCPBSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atividadeCPBS });
      comp.ngOnInit();

      expect(inscricaoCPBSService.query).toHaveBeenCalled();
      expect(inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing).toHaveBeenCalledWith(
        inscricaoCPBSCollection,
        ...additionalInscricaoCPBS.map(expect.objectContaining)
      );
      expect(comp.inscricaoCPBSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const atividadeCPBS: IAtividadeCPBS = { id: 456 };
      const inscricaoCpbs: IInscricaoCPBS = { id: 24308 };
      atividadeCPBS.inscricaoCpbs = inscricaoCpbs;

      activatedRoute.data = of({ atividadeCPBS });
      comp.ngOnInit();

      expect(comp.inscricaoCPBSSharedCollection).toContain(inscricaoCpbs);
      expect(comp.atividadeCPBS).toEqual(atividadeCPBS);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeCPBS>>();
      const atividadeCPBS = { id: 123 };
      jest.spyOn(atividadeCPBSFormService, 'getAtividadeCPBS').mockReturnValue(atividadeCPBS);
      jest.spyOn(atividadeCPBSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeCPBS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atividadeCPBS }));
      saveSubject.complete();

      // THEN
      expect(atividadeCPBSFormService.getAtividadeCPBS).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(atividadeCPBSService.update).toHaveBeenCalledWith(expect.objectContaining(atividadeCPBS));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeCPBS>>();
      const atividadeCPBS = { id: 123 };
      jest.spyOn(atividadeCPBSFormService, 'getAtividadeCPBS').mockReturnValue({ id: null });
      jest.spyOn(atividadeCPBSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeCPBS: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atividadeCPBS }));
      saveSubject.complete();

      // THEN
      expect(atividadeCPBSFormService.getAtividadeCPBS).toHaveBeenCalled();
      expect(atividadeCPBSService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeCPBS>>();
      const atividadeCPBS = { id: 123 };
      jest.spyOn(atividadeCPBSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeCPBS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atividadeCPBSService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInscricaoCPBS', () => {
      it('Should forward to inscricaoCPBSService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(inscricaoCPBSService, 'compareInscricaoCPBS');
        comp.compareInscricaoCPBS(entity, entity2);
        expect(inscricaoCPBSService.compareInscricaoCPBS).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
