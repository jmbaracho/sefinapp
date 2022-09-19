import dayjs from 'dayjs/esm';
import { ITask } from 'app/entities/task/task.model';
import { IAgent } from 'app/entities/agent/agent.model';
import { ICompany } from 'app/entities/company/company.model';

export interface IHotelResult {
  id: number;
  name?: string | null;
  address?: string | null;
  starsCount?: number | null;
  rating?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  task?: Pick<ITask, 'id'> | null;
  agent?: Pick<IAgent, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewHotelResult = Omit<IHotelResult, 'id'> & { id: null };
