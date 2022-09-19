import { CPBSSituacaoEnum } from 'app/entities/enumerations/cpbs-situacao-enum.model';

import { IInscricaoCPBS, NewInscricaoCPBS } from './inscricao-cpbs.model';

export const sampleWithRequiredData: IInscricaoCPBS = {
  id: 18234,
};

export const sampleWithPartialData: IInscricaoCPBS = {
  id: 3391,
  inscricao: 'verde',
  nomeFantasia: 'cultivate Tocantins',
  numDocumento: 'revolutionize Credit Esportes',
  optanteSimplesNacional: false,
  descricaoNaturezaJuridica: 'Account backing',
  logradouro: 'Rial',
  complemento: 'Rua Global Plástico',
};

export const sampleWithFullData: IInscricaoCPBS = {
  id: 54379,
  inscricao: 'Madeira',
  nome: 'Avenida Buckinghamshire',
  nomeFantasia: 'deposit',
  numDocumento: 'auxiliary Infrastructure Won',
  situacao: CPBSSituacaoEnum['ATIVA'],
  optanteSimplesNacional: false,
  codigoNaturezaJuridica: 'responsive target',
  descricaoNaturezaJuridica: 'intuitive Assistant',
  tipoLogradouro: 'Future',
  tituloLogradouro: 'COM Sapatos best-of-breed',
  logradouro: 'Micronésia',
  numero: 'SAS optical Camiseta',
  complemento: 'enterprise incentivize Pre-emptive',
  nomeCidade: 'Investment fuligem',
  nomeBairro: 'Factors',
  numeroCep: 'Future-proofed',
};

export const sampleWithNewData: NewInscricaoCPBS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
