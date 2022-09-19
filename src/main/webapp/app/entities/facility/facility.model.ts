import dayjs from 'dayjs/esm';

export interface IFacility {
  id: number;
  name?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewFacility = Omit<IFacility, 'id'> & { id: null };
