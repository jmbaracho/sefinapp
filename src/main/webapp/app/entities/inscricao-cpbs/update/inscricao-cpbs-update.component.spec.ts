import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InscricaoCPBSFormService } from './inscricao-cpbs-form.service';
import { InscricaoCPBSService } from '../service/inscricao-cpbs.service';
import { IInscricaoCPBS } from '../inscricao-cpbs.model';

import { InscricaoCPBSUpdateComponent } from './inscricao-cpbs-update.component';

describe('InscricaoCPBS Management Update Component', () => {
  let comp: InscricaoCPBSUpdateComponent;
  let fixture: ComponentFixture<InscricaoCPBSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inscricaoCPBSFormService: InscricaoCPBSFormService;
  let inscricaoCPBSService: InscricaoCPBSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InscricaoCPBSUpdateComponent],
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
      .overrideTemplate(InscricaoCPBSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InscricaoCPBSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inscricaoCPBSFormService = TestBed.inject(InscricaoCPBSFormService);
    inscricaoCPBSService = TestBed.inject(InscricaoCPBSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const inscricaoCPBS: IInscricaoCPBS = { id: 456 };

      activatedRoute.data = of({ inscricaoCPBS });
      comp.ngOnInit();

      expect(comp.inscricaoCPBS).toEqual(inscricaoCPBS);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscricaoCPBS>>();
      const inscricaoCPBS = { id: 123 };
      jest.spyOn(inscricaoCPBSFormService, 'getInscricaoCPBS').mockReturnValue(inscricaoCPBS);
      jest.spyOn(inscricaoCPBSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscricaoCPBS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inscricaoCPBS }));
      saveSubject.complete();

      // THEN
      expect(inscricaoCPBSFormService.getInscricaoCPBS).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(inscricaoCPBSService.update).toHaveBeenCalledWith(expect.objectContaining(inscricaoCPBS));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscricaoCPBS>>();
      const inscricaoCPBS = { id: 123 };
      jest.spyOn(inscricaoCPBSFormService, 'getInscricaoCPBS').mockReturnValue({ id: null });
      jest.spyOn(inscricaoCPBSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscricaoCPBS: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inscricaoCPBS }));
      saveSubject.complete();

      // THEN
      expect(inscricaoCPBSFormService.getInscricaoCPBS).toHaveBeenCalled();
      expect(inscricaoCPBSService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscricaoCPBS>>();
      const inscricaoCPBS = { id: 123 };
      jest.spyOn(inscricaoCPBSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscricaoCPBS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inscricaoCPBSService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
