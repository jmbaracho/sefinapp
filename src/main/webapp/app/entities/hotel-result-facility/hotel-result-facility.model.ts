import dayjs from 'dayjs/esm';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';
import { IFacility } from 'app/entities/facility/facility.model';

export interface IHotelResultFacility {
  id: number;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  hotelResult?: Pick<IHotelResult, 'id'> | null;
  facility?: Pick<IFacility, 'id'> | null;
}

export type NewHotelResultFacility = Omit<IHotelResultFacility, 'id'> & { id: null };
