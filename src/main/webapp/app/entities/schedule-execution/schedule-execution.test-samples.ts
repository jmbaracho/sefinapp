import dayjs from 'dayjs/esm';

import { IScheduleExecution, NewScheduleExecution } from './schedule-execution.model';

export const sampleWithRequiredData: IScheduleExecution = {
  id: 69714,
};

export const sampleWithPartialData: IScheduleExecution = {
  id: 21393,
  updatedAt: dayjs('2022-09-18'),
};

export const sampleWithFullData: IScheduleExecution = {
  id: 1453,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewScheduleExecution = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
