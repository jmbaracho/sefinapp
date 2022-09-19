import { IInscricaoCPBS } from 'app/entities/inscricao-cpbs/inscricao-cpbs.model';

export interface IAtividadeCPBS {
  id: number;
  idAtividadeCpbs?: string | null;
  cnae?: string | null;
  descricaoCnae?: string | null;
  principal?: boolean | null;
  idSegmentoIss?: string | null;
  descricaoSegmento?: string | null;
  idListaSerCpbs?: string | null;
  codigoListaSerCpbs?: string | null;
  descricaoListaSerCpbs?: string | null;
  inscricaoCpbs?: Pick<IInscricaoCPBS, 'id' | 'inscricao'> | null;
}

export type NewAtividadeCPBS = Omit<IAtividadeCPBS, 'id'> & { id: null };
