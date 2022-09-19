import dayjs from 'dayjs/esm';

import { IHotelResult, NewHotelResult } from './hotel-result.model';

export const sampleWithRequiredData: IHotelResult = {
  id: 35684,
};

export const sampleWithPartialData: IHotelResult = {
  id: 64253,
  name: 'Borracha Cambridgeshire',
  address: 'technologies system primary',
  createdAt: dayjs('2022-09-19T14:11'),
};

export const sampleWithFullData: IHotelResult = {
  id: 29821,
  name: 'systemic calculating holistic',
  address: 'cross-platform reinvent JBOD',
  starsCount: 26399,
  rating: 26546,
  createdAt: dayjs('2022-09-19T02:10'),
  updatedAt: dayjs('2022-09-19T04:13'),
};

export const sampleWithNewData: NewHotelResult = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
