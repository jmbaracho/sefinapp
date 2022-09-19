import dayjs from 'dayjs/esm';
import { IAgent } from 'app/entities/agent/agent.model';
import { ICompany } from 'app/entities/company/company.model';

export interface ISource {
  id: number;
  url?: string | null;
  enabled?: boolean | null;
  failed?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  agent?: Pick<IAgent, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewSource = Omit<ISource, 'id'> & { id: null };
