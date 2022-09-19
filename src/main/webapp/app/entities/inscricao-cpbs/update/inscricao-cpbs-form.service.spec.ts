import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../inscricao-cpbs.test-samples';

import { InscricaoCPBSFormService } from './inscricao-cpbs-form.service';

describe('InscricaoCPBS Form Service', () => {
  let service: InscricaoCPBSFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InscricaoCPBSFormService);
  });

  describe('Service methods', () => {
    describe('createInscricaoCPBSFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInscricaoCPBSFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inscricao: expect.any(Object),
            nome: expect.any(Object),
            nomeFantasia: expect.any(Object),
            numDocumento: expect.any(Object),
            situacao: expect.any(Object),
            optanteSimplesNacional: expect.any(Object),
            codigoNaturezaJuridica: expect.any(Object),
            descricaoNaturezaJuridica: expect.any(Object),
            tipoLogradouro: expect.any(Object),
            tituloLogradouro: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            nomeCidade: expect.any(Object),
            nomeBairro: expect.any(Object),
            numeroCep: expect.any(Object),
          })
        );
      });

      it('passing IInscricaoCPBS should create a new form with FormGroup', () => {
        const formGroup = service.createInscricaoCPBSFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inscricao: expect.any(Object),
            nome: expect.any(Object),
            nomeFantasia: expect.any(Object),
            numDocumento: expect.any(Object),
            situacao: expect.any(Object),
            optanteSimplesNacional: expect.any(Object),
            codigoNaturezaJuridica: expect.any(Object),
            descricaoNaturezaJuridica: expect.any(Object),
            tipoLogradouro: expect.any(Object),
            tituloLogradouro: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            nomeCidade: expect.any(Object),
            nomeBairro: expect.any(Object),
            numeroCep: expect.any(Object),
          })
        );
      });
    });

    describe('getInscricaoCPBS', () => {
      it('should return NewInscricaoCPBS for default InscricaoCPBS initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInscricaoCPBSFormGroup(sampleWithNewData);

        const inscricaoCPBS = service.getInscricaoCPBS(formGroup) as any;

        expect(inscricaoCPBS).toMatchObject(sampleWithNewData);
      });

      it('should return NewInscricaoCPBS for empty InscricaoCPBS initial value', () => {
        const formGroup = service.createInscricaoCPBSFormGroup();

        const inscricaoCPBS = service.getInscricaoCPBS(formGroup) as any;

        expect(inscricaoCPBS).toMatchObject({});
      });

      it('should return IInscricaoCPBS', () => {
        const formGroup = service.createInscricaoCPBSFormGroup(sampleWithRequiredData);

        const inscricaoCPBS = service.getInscricaoCPBS(formGroup) as any;

        expect(inscricaoCPBS).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInscricaoCPBS should not enable id FormControl', () => {
        const formGroup = service.createInscricaoCPBSFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInscricaoCPBS should disable id FormControl', () => {
        const formGroup = service.createInscricaoCPBSFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
