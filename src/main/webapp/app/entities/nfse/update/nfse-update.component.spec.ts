import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NfseFormService } from './nfse-form.service';
import { NfseService } from '../service/nfse.service';
import { INfse } from '../nfse.model';
import { IInscricaoCPBS } from 'app/entities/inscricao-cpbs/inscricao-cpbs.model';
import { InscricaoCPBSService } from 'app/entities/inscricao-cpbs/service/inscricao-cpbs.service';

import { NfseUpdateComponent } from './nfse-update.component';

describe('Nfse Management Update Component', () => {
  let comp: NfseUpdateComponent;
  let fixture: ComponentFixture<NfseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nfseFormService: NfseFormService;
  let nfseService: NfseService;
  let inscricaoCPBSService: InscricaoCPBSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NfseUpdateComponent],
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
      .overrideTemplate(NfseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NfseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nfseFormService = TestBed.inject(NfseFormService);
    nfseService = TestBed.inject(NfseService);
    inscricaoCPBSService = TestBed.inject(InscricaoCPBSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InscricaoCPBS query and add missing value', () => {
      const nfse: INfse = { id: 456 };
      const inscricaoCpbs: IInscricaoCPBS = { id: 54002 };
      nfse.inscricaoCpbs = inscricaoCpbs;

      const inscricaoCPBSCollection: IInscricaoCPBS[] = [{ id: 77122 }];
      jest.spyOn(inscricaoCPBSService, 'query').mockReturnValue(of(new HttpResponse({ body: inscricaoCPBSCollection })));
      const additionalInscricaoCPBS = [inscricaoCpbs];
      const expectedCollection: IInscricaoCPBS[] = [...additionalInscricaoCPBS, ...inscricaoCPBSCollection];
      jest.spyOn(inscricaoCPBSService, 'addInscricaoCPBSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nfse });
      comp.ngOnInit();

      expect(inscricaoCPBSService.query).toHaveBeenCalled();
      expect(inscricaoCPBSService.addInscricaoCPBSToCollectionIfMissing).toHaveBeenCalledWith(
        inscricaoCPBSCollection,
        ...additionalInscricaoCPBS.map(expect.objectContaining)
      );
      expect(comp.inscricaoCPBSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nfse: INfse = { id: 456 };
      const inscricaoCpbs: IInscricaoCPBS = { id: 17952 };
      nfse.inscricaoCpbs = inscricaoCpbs;

      activatedRoute.data = of({ nfse });
      comp.ngOnInit();

      expect(comp.inscricaoCPBSSharedCollection).toContain(inscricaoCpbs);
      expect(comp.nfse).toEqual(nfse);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfse>>();
      const nfse = { id: 123 };
      jest.spyOn(nfseFormService, 'getNfse').mockReturnValue(nfse);
      jest.spyOn(nfseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nfse }));
      saveSubject.complete();

      // THEN
      expect(nfseFormService.getNfse).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nfseService.update).toHaveBeenCalledWith(expect.objectContaining(nfse));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfse>>();
      const nfse = { id: 123 };
      jest.spyOn(nfseFormService, 'getNfse').mockReturnValue({ id: null });
      jest.spyOn(nfseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfse: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nfse }));
      saveSubject.complete();

      // THEN
      expect(nfseFormService.getNfse).toHaveBeenCalled();
      expect(nfseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfse>>();
      const nfse = { id: 123 };
      jest.spyOn(nfseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nfseService.update).toHaveBeenCalled();
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
