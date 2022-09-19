import dayjs from 'dayjs/esm';
import { ITask } from 'app/entities/task/task.model';

export interface IRawData {
  id: number;
  content?: string | null;
  processed?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  task?: Pick<ITask, 'id'> | null;
}

export type NewRawData = Omit<IRawData, 'id'> & { id: null };
