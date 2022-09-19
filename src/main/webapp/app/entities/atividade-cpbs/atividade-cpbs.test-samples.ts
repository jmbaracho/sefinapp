import { IAtividadeCPBS, NewAtividadeCPBS } from './atividade-cpbs.model';

export const sampleWithRequiredData: IAtividadeCPBS = {
  id: 38637,
};

export const sampleWithPartialData: IAtividadeCPBS = {
  id: 63918,
  idAtividadeCpbs: 'Ergonômico',
  idSegmentoIss: 'invoice Bedfordshire',
  descricaoSegmento: 'transitional',
  idListaSerCpbs: 'logistical laranja',
};

export const sampleWithFullData: IAtividadeCPBS = {
  id: 53185,
  idAtividadeCpbs: 'Assimilated',
  cnae: 'Ilha',
  descricaoCnae: 'Synergistic Amapá',
  principal: true,
  idSegmentoIss: 'púrpura',
  descricaoSegmento: 'vermelho Casa',
  idListaSerCpbs: 'AI Up-sized transmitting',
  codigoListaSerCpbs: 'Kip',
  descricaoListaSerCpbs: 'Plástico Avenida',
};

export const sampleWithNewData: NewAtividadeCPBS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
