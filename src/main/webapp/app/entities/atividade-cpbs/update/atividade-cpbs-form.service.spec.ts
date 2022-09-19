import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../atividade-cpbs.test-samples';

import { AtividadeCPBSFormService } from './atividade-cpbs-form.service';

describe('AtividadeCPBS Form Service', () => {
  let service: AtividadeCPBSFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtividadeCPBSFormService);
  });

  describe('Service methods', () => {
    describe('createAtividadeCPBSFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAtividadeCPBSFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAtividadeCpbs: expect.any(Object),
            cnae: expect.any(Object),
            descricaoCnae: expect.any(Object),
            principal: expect.any(Object),
            idSegmentoIss: expect.any(Object),
            descricaoSegmento: expect.any(Object),
            idListaSerCpbs: expect.any(Object),
            codigoListaSerCpbs: expect.any(Object),
            descricaoListaSerCpbs: expect.any(Object),
            inscricaoCpbs: expect.any(Object),
          })
        );
      });

      it('passing IAtividadeCPBS should create a new form with FormGroup', () => {
        const formGroup = service.createAtividadeCPBSFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAtividadeCpbs: expect.any(Object),
            cnae: expect.any(Object),
            descricaoCnae: expect.any(Object),
            principal: expect.any(Object),
            idSegmentoIss: expect.any(Object),
            descricaoSegmento: expect.any(Object),
            idListaSerCpbs: expect.any(Object),
            codigoListaSerCpbs: expect.any(Object),
            descricaoListaSerCpbs: expect.any(Object),
            inscricaoCpbs: expect.any(Object),
          })
        );
      });
    });

    describe('getAtividadeCPBS', () => {
      it('should return NewAtividadeCPBS for default AtividadeCPBS initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAtividadeCPBSFormGroup(sampleWithNewData);

        const atividadeCPBS = service.getAtividadeCPBS(formGroup) as any;

        expect(atividadeCPBS).toMatchObject(sampleWithNewData);
      });

      it('should return NewAtividadeCPBS for empty AtividadeCPBS initial value', () => {
        const formGroup = service.createAtividadeCPBSFormGroup();

        const atividadeCPBS = service.getAtividadeCPBS(formGroup) as any;

        expect(atividadeCPBS).toMatchObject({});
      });

      it('should return IAtividadeCPBS', () => {
        const formGroup = service.createAtividadeCPBSFormGroup(sampleWithRequiredData);

        const atividadeCPBS = service.getAtividadeCPBS(formGroup) as any;

        expect(atividadeCPBS).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAtividadeCPBS should not enable id FormControl', () => {
        const formGroup = service.createAtividadeCPBSFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAtividadeCPBS should disable id FormControl', () => {
        const formGroup = service.createAtividadeCPBSFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
