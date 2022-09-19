import dayjs from 'dayjs/esm';

import { IFacility, NewFacility } from './facility.model';

export const sampleWithRequiredData: IFacility = {
  id: 86127,
};

export const sampleWithPartialData: IFacility = {
  id: 76138,
  createdAt: dayjs('2022-09-19T12:04'),
};

export const sampleWithFullData: IFacility = {
  id: 5746,
  name: 'Alameda Administrator',
  createdAt: dayjs('2022-09-18T22:07'),
  updatedAt: dayjs('2022-09-19T10:10'),
};

export const sampleWithNewData: NewFacility = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
