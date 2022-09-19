import dayjs from 'dayjs/esm';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';

export interface IRoom {
  id: number;
  numberOfPeople?: number | null;
  price?: number | null;
  periodType?: number | null;
  checkInDate?: dayjs.Dayjs | null;
  checkOutDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  hotelResult?: Pick<IHotelResult, 'id'> | null;
}

export type NewRoom = Omit<IRoom, 'id'> & { id: null };
