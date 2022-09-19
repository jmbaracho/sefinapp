import dayjs from 'dayjs/esm';

export interface IAgent {
  id: number;
  name?: string | null;
  maxBlocksPerTime?: number | null;
  blockSize?: number | null;
  intervalBetweenBlocks?: number | null;
  intervalBetweenTasks?: number | null;
  maxAttempts?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewAgent = Omit<IAgent, 'id'> & { id: null };
