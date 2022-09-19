import dayjs from 'dayjs/esm';

import { IHotelResultFacility, NewHotelResultFacility } from './hotel-result-facility.model';

export const sampleWithRequiredData: IHotelResultFacility = {
  id: 9687,
};

export const sampleWithPartialData: IHotelResultFacility = {
  id: 1532,
  createdAt: dayjs('2022-09-19T17:25'),
  updatedAt: dayjs('2022-09-19T03:16'),
};

export const sampleWithFullData: IHotelResultFacility = {
  id: 18322,
  createdAt: dayjs('2022-09-19T02:26'),
  updatedAt: dayjs('2022-09-19T15:29'),
};

export const sampleWithNewData: NewHotelResultFacility = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
