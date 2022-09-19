import { CPBSSituacaoEnum } from 'app/entities/enumerations/cpbs-situacao-enum.model';

export interface IInscricaoCPBS {
  id: number;
  inscricao?: string | null;
  nome?: string | null;
  nomeFantasia?: string | null;
  numDocumento?: string | null;
  situacao?: CPBSSituacaoEnum | null;
  optanteSimplesNacional?: boolean | null;
  codigoNaturezaJuridica?: string | null;
  descricaoNaturezaJuridica?: string | null;
  tipoLogradouro?: string | null;
  tituloLogradouro?: string | null;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  nomeCidade?: string | null;
  nomeBairro?: string | null;
  numeroCep?: string | null;
}

export type NewInscricaoCPBS = Omit<IInscricaoCPBS, 'id'> & { id: null };
