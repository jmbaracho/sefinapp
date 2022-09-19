import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nfse.test-samples';

import { NfseFormService } from './nfse-form.service';

describe('Nfse Form Service', () => {
  let service: NfseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NfseFormService);
  });

  describe('Service methods', () => {
    describe('createNfseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNfseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idNfse: expect.any(Object),
            competencia: expect.any(Object),
            dataEmissao: expect.any(Object),
            ip: expect.any(Object),
            optanteSimplesNacional: expect.any(Object),
            outrasInformacoes: expect.any(Object),
            idNfseSubstituida: expect.any(Object),
            usuario: expect.any(Object),
            statusNfse: expect.any(Object),
            naturezaOperacao: expect.any(Object),
            rps: expect.any(Object),
            inscricaoPrestador: expect.any(Object),
            tipoDocDigitado: expect.any(Object),
            statusAceite: expect.any(Object),
            exclusaoLogica: expect.any(Object),
            ajustada: expect.any(Object),
            observacaoAjuste: expect.any(Object),
            aliquotaServico: expect.any(Object),
            art: expect.any(Object),
            baseCalculo: expect.any(Object),
            codigoObra: expect.any(Object),
            valorServico: expect.any(Object),
            valorPis: expect.any(Object),
            valorLiquidoNfse: expect.any(Object),
            valorIssRetido: expect.any(Object),
            valorIssOutroMunicipio: expect.any(Object),
            valorIss: expect.any(Object),
            valorIr: expect.any(Object),
            valorInss: expect.any(Object),
            valorDescontoCondicionado: expect.any(Object),
            valorDescontoIncondicionado: expect.any(Object),
            valorDeducoes: expect.any(Object),
            valorCsll: expect.any(Object),
            valorCredito: expect.any(Object),
            valorCofins: expect.any(Object),
            outrasRetencoes: expect.any(Object),
            issRetido: expect.any(Object),
            discriminacao: expect.any(Object),
            municipioPrestacaoServico: expect.any(Object),
            paisPrestacaoServico: expect.any(Object),
            codRegimeEspecialTributacao: expect.any(Object),
            codigoTributacaoMunicipio: expect.any(Object),
            itemListaServicoMunicipio: expect.any(Object),
            cpfCnpjTomador: expect.any(Object),
            cpfCnpjPrestador: expect.any(Object),
            inscricaoMunicipalTomador: expect.any(Object),
            indicadorCpfCnpjTomador: expect.any(Object),
            indicadorCpfCnpjPrestador: expect.any(Object),
            paisTomador: expect.any(Object),
            paisPrestador: expect.any(Object),
            tipoTomador: expect.any(Object),
            codCidadeTomadorIBGE: expect.any(Object),
            codCidadePrestadorIBGE: expect.any(Object),
            tipoPrestador: expect.any(Object),
            numeroEmpenho: expect.any(Object),
            tipoTributacaoPrestExterno: expect.any(Object),
            cepPrestador: expect.any(Object),
            cepTomador: expect.any(Object),
            idTipoAnexoSimplesNacional: expect.any(Object),
            idBloqueioNfse: expect.any(Object),
            idPgdasd2018Atividade: expect.any(Object),
            inscricaoCpbs: expect.any(Object),
          })
        );
      });

      it('passing INfse should create a new form with FormGroup', () => {
        const formGroup = service.createNfseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idNfse: expect.any(Object),
            competencia: expect.any(Object),
            dataEmissao: expect.any(Object),
            ip: expect.any(Object),
            optanteSimplesNacional: expect.any(Object),
            outrasInformacoes: expect.any(Object),
            idNfseSubstituida: expect.any(Object),
            usuario: expect.any(Object),
            statusNfse: expect.any(Object),
            naturezaOperacao: expect.any(Object),
            rps: expect.any(Object),
            inscricaoPrestador: expect.any(Object),
            tipoDocDigitado: expect.any(Object),
            statusAceite: expect.any(Object),
            exclusaoLogica: expect.any(Object),
            ajustada: expect.any(Object),
            observacaoAjuste: expect.any(Object),
            aliquotaServico: expect.any(Object),
            art: expect.any(Object),
            baseCalculo: expect.any(Object),
            codigoObra: expect.any(Object),
            valorServico: expect.any(Object),
            valorPis: expect.any(Object),
            valorLiquidoNfse: expect.any(Object),
            valorIssRetido: expect.any(Object),
            valorIssOutroMunicipio: expect.any(Object),
            valorIss: expect.any(Object),
            valorIr: expect.any(Object),
            valorInss: expect.any(Object),
            valorDescontoCondicionado: expect.any(Object),
            valorDescontoIncondicionado: expect.any(Object),
            valorDeducoes: expect.any(Object),
            valorCsll: expect.any(Object),
            valorCredito: expect.any(Object),
            valorCofins: expect.any(Object),
            outrasRetencoes: expect.any(Object),
            issRetido: expect.any(Object),
            discriminacao: expect.any(Object),
            municipioPrestacaoServico: expect.any(Object),
            paisPrestacaoServico: expect.any(Object),
            codRegimeEspecialTributacao: expect.any(Object),
            codigoTributacaoMunicipio: expect.any(Object),
            itemListaServicoMunicipio: expect.any(Object),
            cpfCnpjTomador: expect.any(Object),
            cpfCnpjPrestador: expect.any(Object),
            inscricaoMunicipalTomador: expect.any(Object),
            indicadorCpfCnpjTomador: expect.any(Object),
            indicadorCpfCnpjPrestador: expect.any(Object),
            paisTomador: expect.any(Object),
            paisPrestador: expect.any(Object),
            tipoTomador: expect.any(Object),
            codCidadeTomadorIBGE: expect.any(Object),
            codCidadePrestadorIBGE: expect.any(Object),
            tipoPrestador: expect.any(Object),
            numeroEmpenho: expect.any(Object),
            tipoTributacaoPrestExterno: expect.any(Object),
            cepPrestador: expect.any(Object),
            cepTomador: expect.any(Object),
            idTipoAnexoSimplesNacional: expect.any(Object),
            idBloqueioNfse: expect.any(Object),
            idPgdasd2018Atividade: expect.any(Object),
            inscricaoCpbs: expect.any(Object),
          })
        );
      });
    });

    describe('getNfse', () => {
      it('should return NewNfse for default Nfse initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNfseFormGroup(sampleWithNewData);

        const nfse = service.getNfse(formGroup) as any;

        expect(nfse).toMatchObject(sampleWithNewData);
      });

      it('should return NewNfse for empty Nfse initial value', () => {
        const formGroup = service.createNfseFormGroup();

        const nfse = service.getNfse(formGroup) as any;

        expect(nfse).toMatchObject({});
      });

      it('should return INfse', () => {
        const formGroup = service.createNfseFormGroup(sampleWithRequiredData);

        const nfse = service.getNfse(formGroup) as any;

        expect(nfse).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INfse should not enable id FormControl', () => {
        const formGroup = service.createNfseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNfse should disable id FormControl', () => {
        const formGroup = service.createNfseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
