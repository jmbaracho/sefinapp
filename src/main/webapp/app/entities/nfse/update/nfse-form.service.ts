import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INfse, NewNfse } from '../nfse.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INfse for edit and NewNfseFormGroupInput for create.
 */
type NfseFormGroupInput = INfse | PartialWithRequiredKeyOf<NewNfse>;

type NfseFormDefaults = Pick<NewNfse, 'id'>;

type NfseFormGroupContent = {
  id: FormControl<INfse['id'] | NewNfse['id']>;
  idNfse: FormControl<INfse['idNfse']>;
  competencia: FormControl<INfse['competencia']>;
  dataEmissao: FormControl<INfse['dataEmissao']>;
  ip: FormControl<INfse['ip']>;
  optanteSimplesNacional: FormControl<INfse['optanteSimplesNacional']>;
  outrasInformacoes: FormControl<INfse['outrasInformacoes']>;
  idNfseSubstituida: FormControl<INfse['idNfseSubstituida']>;
  usuario: FormControl<INfse['usuario']>;
  statusNfse: FormControl<INfse['statusNfse']>;
  naturezaOperacao: FormControl<INfse['naturezaOperacao']>;
  rps: FormControl<INfse['rps']>;
  inscricaoPrestador: FormControl<INfse['inscricaoPrestador']>;
  tipoDocDigitado: FormControl<INfse['tipoDocDigitado']>;
  statusAceite: FormControl<INfse['statusAceite']>;
  exclusaoLogica: FormControl<INfse['exclusaoLogica']>;
  ajustada: FormControl<INfse['ajustada']>;
  observacaoAjuste: FormControl<INfse['observacaoAjuste']>;
  aliquotaServico: FormControl<INfse['aliquotaServico']>;
  art: FormControl<INfse['art']>;
  baseCalculo: FormControl<INfse['baseCalculo']>;
  codigoObra: FormControl<INfse['codigoObra']>;
  valorServico: FormControl<INfse['valorServico']>;
  valorPis: FormControl<INfse['valorPis']>;
  valorLiquidoNfse: FormControl<INfse['valorLiquidoNfse']>;
  valorIssRetido: FormControl<INfse['valorIssRetido']>;
  valorIssOutroMunicipio: FormControl<INfse['valorIssOutroMunicipio']>;
  valorIss: FormControl<INfse['valorIss']>;
  valorIr: FormControl<INfse['valorIr']>;
  valorInss: FormControl<INfse['valorInss']>;
  valorDescontoCondicionado: FormControl<INfse['valorDescontoCondicionado']>;
  valorDescontoIncondicionado: FormControl<INfse['valorDescontoIncondicionado']>;
  valorDeducoes: FormControl<INfse['valorDeducoes']>;
  valorCsll: FormControl<INfse['valorCsll']>;
  valorCredito: FormControl<INfse['valorCredito']>;
  valorCofins: FormControl<INfse['valorCofins']>;
  outrasRetencoes: FormControl<INfse['outrasRetencoes']>;
  issRetido: FormControl<INfse['issRetido']>;
  discriminacao: FormControl<INfse['discriminacao']>;
  municipioPrestacaoServico: FormControl<INfse['municipioPrestacaoServico']>;
  paisPrestacaoServico: FormControl<INfse['paisPrestacaoServico']>;
  codRegimeEspecialTributacao: FormControl<INfse['codRegimeEspecialTributacao']>;
  codigoTributacaoMunicipio: FormControl<INfse['codigoTributacaoMunicipio']>;
  itemListaServicoMunicipio: FormControl<INfse['itemListaServicoMunicipio']>;
  cpfCnpjTomador: FormControl<INfse['cpfCnpjTomador']>;
  cpfCnpjPrestador: FormControl<INfse['cpfCnpjPrestador']>;
  inscricaoMunicipalTomador: FormControl<INfse['inscricaoMunicipalTomador']>;
  indicadorCpfCnpjTomador: FormControl<INfse['indicadorCpfCnpjTomador']>;
  indicadorCpfCnpjPrestador: FormControl<INfse['indicadorCpfCnpjPrestador']>;
  paisTomador: FormControl<INfse['paisTomador']>;
  paisPrestador: FormControl<INfse['paisPrestador']>;
  tipoTomador: FormControl<INfse['tipoTomador']>;
  codCidadeTomadorIBGE: FormControl<INfse['codCidadeTomadorIBGE']>;
  codCidadePrestadorIBGE: FormControl<INfse['codCidadePrestadorIBGE']>;
  tipoPrestador: FormControl<INfse['tipoPrestador']>;
  numeroEmpenho: FormControl<INfse['numeroEmpenho']>;
  tipoTributacaoPrestExterno: FormControl<INfse['tipoTributacaoPrestExterno']>;
  cepPrestador: FormControl<INfse['cepPrestador']>;
  cepTomador: FormControl<INfse['cepTomador']>;
  idTipoAnexoSimplesNacional: FormControl<INfse['idTipoAnexoSimplesNacional']>;
  idBloqueioNfse: FormControl<INfse['idBloqueioNfse']>;
  idPgdasd2018Atividade: FormControl<INfse['idPgdasd2018Atividade']>;
  inscricaoCpbs: FormControl<INfse['inscricaoCpbs']>;
};

export type NfseFormGroup = FormGroup<NfseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NfseFormService {
  createNfseFormGroup(nfse: NfseFormGroupInput = { id: null }): NfseFormGroup {
    const nfseRawValue = {
      ...this.getFormDefaults(),
      ...nfse,
    };
    return new FormGroup<NfseFormGroupContent>({
      id: new FormControl(
        { value: nfseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idNfse: new FormControl(nfseRawValue.idNfse),
      competencia: new FormControl(nfseRawValue.competencia),
      dataEmissao: new FormControl(nfseRawValue.dataEmissao),
      ip: new FormControl(nfseRawValue.ip),
      optanteSimplesNacional: new FormControl(nfseRawValue.optanteSimplesNacional),
      outrasInformacoes: new FormControl(nfseRawValue.outrasInformacoes),
      idNfseSubstituida: new FormControl(nfseRawValue.idNfseSubstituida),
      usuario: new FormControl(nfseRawValue.usuario),
      statusNfse: new FormControl(nfseRawValue.statusNfse),
      naturezaOperacao: new FormControl(nfseRawValue.naturezaOperacao),
      rps: new FormControl(nfseRawValue.rps),
      inscricaoPrestador: new FormControl(nfseRawValue.inscricaoPrestador),
      tipoDocDigitado: new FormControl(nfseRawValue.tipoDocDigitado),
      statusAceite: new FormControl(nfseRawValue.statusAceite),
      exclusaoLogica: new FormControl(nfseRawValue.exclusaoLogica),
      ajustada: new FormControl(nfseRawValue.ajustada),
      observacaoAjuste: new FormControl(nfseRawValue.observacaoAjuste),
      aliquotaServico: new FormControl(nfseRawValue.aliquotaServico),
      art: new FormControl(nfseRawValue.art),
      baseCalculo: new FormControl(nfseRawValue.baseCalculo),
      codigoObra: new FormControl(nfseRawValue.codigoObra),
      valorServico: new FormControl(nfseRawValue.valorServico),
      valorPis: new FormControl(nfseRawValue.valorPis),
      valorLiquidoNfse: new FormControl(nfseRawValue.valorLiquidoNfse),
      valorIssRetido: new FormControl(nfseRawValue.valorIssRetido),
      valorIssOutroMunicipio: new FormControl(nfseRawValue.valorIssOutroMunicipio),
      valorIss: new FormControl(nfseRawValue.valorIss),
      valorIr: new FormControl(nfseRawValue.valorIr),
      valorInss: new FormControl(nfseRawValue.valorInss),
      valorDescontoCondicionado: new FormControl(nfseRawValue.valorDescontoCondicionado),
      valorDescontoIncondicionado: new FormControl(nfseRawValue.valorDescontoIncondicionado),
      valorDeducoes: new FormControl(nfseRawValue.valorDeducoes),
      valorCsll: new FormControl(nfseRawValue.valorCsll),
      valorCredito: new FormControl(nfseRawValue.valorCredito),
      valorCofins: new FormControl(nfseRawValue.valorCofins),
      outrasRetencoes: new FormControl(nfseRawValue.outrasRetencoes),
      issRetido: new FormControl(nfseRawValue.issRetido),
      discriminacao: new FormControl(nfseRawValue.discriminacao),
      municipioPrestacaoServico: new FormControl(nfseRawValue.municipioPrestacaoServico),
      paisPrestacaoServico: new FormControl(nfseRawValue.paisPrestacaoServico),
      codRegimeEspecialTributacao: new FormControl(nfseRawValue.codRegimeEspecialTributacao),
      codigoTributacaoMunicipio: new FormControl(nfseRawValue.codigoTributacaoMunicipio),
      itemListaServicoMunicipio: new FormControl(nfseRawValue.itemListaServicoMunicipio),
      cpfCnpjTomador: new FormControl(nfseRawValue.cpfCnpjTomador),
      cpfCnpjPrestador: new FormControl(nfseRawValue.cpfCnpjPrestador),
      inscricaoMunicipalTomador: new FormControl(nfseRawValue.inscricaoMunicipalTomador),
      indicadorCpfCnpjTomador: new FormControl(nfseRawValue.indicadorCpfCnpjTomador),
      indicadorCpfCnpjPrestador: new FormControl(nfseRawValue.indicadorCpfCnpjPrestador),
      paisTomador: new FormControl(nfseRawValue.paisTomador),
      paisPrestador: new FormControl(nfseRawValue.paisPrestador),
      tipoTomador: new FormControl(nfseRawValue.tipoTomador),
      codCidadeTomadorIBGE: new FormControl(nfseRawValue.codCidadeTomadorIBGE),
      codCidadePrestadorIBGE: new FormControl(nfseRawValue.codCidadePrestadorIBGE),
      tipoPrestador: new FormControl(nfseRawValue.tipoPrestador),
      numeroEmpenho: new FormControl(nfseRawValue.numeroEmpenho),
      tipoTributacaoPrestExterno: new FormControl(nfseRawValue.tipoTributacaoPrestExterno),
      cepPrestador: new FormControl(nfseRawValue.cepPrestador),
      cepTomador: new FormControl(nfseRawValue.cepTomador),
      idTipoAnexoSimplesNacional: new FormControl(nfseRawValue.idTipoAnexoSimplesNacional),
      idBloqueioNfse: new FormControl(nfseRawValue.idBloqueioNfse),
      idPgdasd2018Atividade: new FormControl(nfseRawValue.idPgdasd2018Atividade),
      inscricaoCpbs: new FormControl(nfseRawValue.inscricaoCpbs),
    });
  }

  getNfse(form: NfseFormGroup): INfse | NewNfse {
    return form.getRawValue() as INfse | NewNfse;
  }

  resetForm(form: NfseFormGroup, nfse: NfseFormGroupInput): void {
    const nfseRawValue = { ...this.getFormDefaults(), ...nfse };
    form.reset(
      {
        ...nfseRawValue,
        id: { value: nfseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NfseFormDefaults {
    return {
      id: null,
    };
  }
}
