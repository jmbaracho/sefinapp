import dayjs from 'dayjs/esm';

import { IRoom, NewRoom } from './room.model';

export const sampleWithRequiredData: IRoom = {
  id: 302,
};

export const sampleWithPartialData: IRoom = {
  id: 3705,
  numberOfPeople: 64594,
  periodType: 27081,
  checkOutDate: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-18T21:08'),
};

export const sampleWithFullData: IRoom = {
  id: 59376,
  numberOfPeople: 96390,
  price: 68831,
  periodType: 91197,
  checkInDate: dayjs('2022-09-19'),
  checkOutDate: dayjs('2022-09-19'),
  createdAt: dayjs('2022-09-19T17:03'),
  updatedAt: dayjs('2022-09-19T09:58'),
};

export const sampleWithNewData: NewRoom = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
