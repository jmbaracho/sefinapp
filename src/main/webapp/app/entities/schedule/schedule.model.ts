import dayjs from 'dayjs/esm';
import { IAgent } from 'app/entities/agent/agent.model';

export interface ISchedule {
  id: number;
  rule?: string | null;
  firstInterval?: number | null;
  secondInterval?: number | null;
  thirdInterval?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  agent?: Pick<IAgent, 'id'> | null;
}

export type NewSchedule = Omit<ISchedule, 'id'> & { id: null };
