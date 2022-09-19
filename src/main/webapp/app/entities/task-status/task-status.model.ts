import dayjs from 'dayjs/esm';

export interface ITaskStatus {
  id: number;
  name?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewTaskStatus = Omit<ITaskStatus, 'id'> & { id: null };
