import dayjs from 'dayjs/esm';

import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 37978,
};

export const sampleWithPartialData: ITask = {
  id: 99632,
  source: 'Up-sized Queijo Representative',
  attemptCount: 50690,
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithFullData: ITask = {
  id: 88041,
  source: 'back-end',
  attemptCount: 90788,
  targetDateInterval: 98258,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-18'),
};

export const sampleWithNewData: NewTask = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
