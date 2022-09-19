import dayjs from 'dayjs/esm';
import { ISchedule } from 'app/entities/schedule/schedule.model';

export interface IScheduleExecution {
  id: number;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  schedule?: Pick<ISchedule, 'id'> | null;
}

export type NewScheduleExecution = Omit<IScheduleExecution, 'id'> & { id: null };
