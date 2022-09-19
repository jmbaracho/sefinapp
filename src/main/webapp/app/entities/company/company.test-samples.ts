import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
  cnpj: 'Dinamarca Sergipe',
};

export const sampleWithPartialData: ICompany = {
  id: 48552,
  name: 'sensor',
  cnpj: 'Granito',
  createdAt: dayjs('2022-09-19'),
};

export const sampleWithFullData: ICompany = {
  id: 69135,
  name: 'Benin Avenida',
  cnpj: 'Devolved',
  address: 'Austrália',
  createdAt: dayjs('2022-09-19'),
  updatedAt: dayjs('2022-09-18'),
};

export const sampleWithNewData: NewCompany = {
  cnpj: 'Avenida Ilha 1080p',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
