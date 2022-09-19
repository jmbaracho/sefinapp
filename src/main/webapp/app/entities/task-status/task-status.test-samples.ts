import dayjs from 'dayjs/esm';

import { ITaskStatus, NewTaskStatus } from './task-status.model';

export const sampleWithRequiredData: ITaskStatus = {
  id: 24226,
};

export const sampleWithPartialData: ITaskStatus = {
  id: 60708,
  createdAt: dayjs('2022-09-19'),
};

export const sampleWithFullData: ITaskStatus = {
  id: 2301,
  name: 'one-to-one',
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewTaskStatus = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
