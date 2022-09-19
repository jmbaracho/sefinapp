import dayjs from 'dayjs/esm';

import { IRawData, NewRawData } from './raw-data.model';

export const sampleWithRequiredData: IRawData = {
  id: 1677,
};

export const sampleWithPartialData: IRawData = {
  id: 25173,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithFullData: IRawData = {
  id: 82127,
  content: 'Goiás',
  processed: true,
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewRawData = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
