import dayjs from 'dayjs/esm';

import { IAgent, NewAgent } from './agent.model';

export const sampleWithRequiredData: IAgent = {
  id: 33038,
};

export const sampleWithPartialData: IAgent = {
  id: 60157,
  name: 'Universal',
  intervalBetweenBlocks: 78841,
  intervalBetweenTasks: 59351,
  updatedAt: dayjs('2022-09-18'),
};

export const sampleWithFullData: IAgent = {
  id: 85026,
  name: 'Computadores methodologies',
  maxBlocksPerTime: 43634,
  blockSize: 46491,
  intervalBetweenBlocks: 13150,
  intervalBetweenTasks: 73466,
  maxAttempts: 57716,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewAgent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
