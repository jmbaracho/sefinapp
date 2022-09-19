import dayjs from 'dayjs/esm';

import { ISource, NewSource } from './source.model';

export const sampleWithRequiredData: ISource = {
  id: 52141,
};

export const sampleWithPartialData: ISource = {
  id: 537,
  url: 'http://maria júlia.org',
  enabled: true,
  failed: false,
};

export const sampleWithFullData: ISource = {
  id: 97305,
  url: 'https://maria luiza.br',
  enabled: false,
  failed: true,
  createdAt: dayjs('2022-09-18'),
  updatedAt: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewSource = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
