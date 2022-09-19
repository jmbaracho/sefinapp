import dayjs from 'dayjs/esm';

export interface ICompany {
  id: number;
  name?: string | null;
  cnpj?: string | null;
  address?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
