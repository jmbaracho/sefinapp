import dayjs from 'dayjs/esm';

import { ISchedule, NewSchedule } from './schedule.model';

export const sampleWithRequiredData: ISchedule = {
  id: 76377,
};

export const sampleWithPartialData: ISchedule = {
  id: 60147,
  rule: 'pele quantifying azul',
  thirdInterval: 17217,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-18'),
};

export const sampleWithFullData: ISchedule = {
  id: 6853,
  rule: 'Technician Adaptive',
  firstInterval: 3489,
  secondInterval: 50152,
  thirdInterval: 32353,
  createdAt: dayjs('2022-09-18'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewSchedule = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
