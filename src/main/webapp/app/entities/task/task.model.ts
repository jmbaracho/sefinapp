import dayjs from 'dayjs/esm';
import { ITaskStatus } from 'app/entities/task-status/task-status.model';
import { IScheduleExecution } from 'app/entities/schedule-execution/schedule-execution.model';

export interface ITask {
  id: number;
  source?: string | null;
  attemptCount?: number | null;
  targetDateInterval?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  status?: Pick<ITaskStatus, 'id'> | null;
  scheduleExecution?: Pick<IScheduleExecution, 'id'> | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
