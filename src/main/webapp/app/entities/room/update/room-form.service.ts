import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRoom, NewRoom } from '../room.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRoom for edit and NewRoomFormGroupInput for create.
 */
type RoomFormGroupInput = IRoom | PartialWithRequiredKeyOf<NewRoom>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRoom | NewRoom> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type RoomFormRawValue = FormValueOf<IRoom>;

type NewRoomFormRawValue = FormValueOf<NewRoom>;

type RoomFormDefaults = Pick<NewRoom, 'id' | 'createdAt' | 'updatedAt'>;

type RoomFormGroupContent = {
  id: FormControl<RoomFormRawValue['id'] | NewRoom['id']>;
  numberOfPeople: FormControl<RoomFormRawValue['numberOfPeople']>;
  price: FormControl<RoomFormRawValue['price']>;
  periodType: FormControl<RoomFormRawValue['periodType']>;
  checkInDate: FormControl<RoomFormRawValue['checkInDate']>;
  checkOutDate: FormControl<RoomFormRawValue['checkOutDate']>;
  createdAt: FormControl<RoomFormRawValue['createdAt']>;
  updatedAt: FormControl<RoomFormRawValue['updatedAt']>;
  hotelResult: FormControl<RoomFormRawValue['hotelResult']>;
};

export type RoomFormGroup = FormGroup<RoomFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RoomFormService {
  createRoomFormGroup(room: RoomFormGroupInput = { id: null }): RoomFormGroup {
    const roomRawValue = this.convertRoomToRoomRawValue({
      ...this.getFormDefaults(),
      ...room,
    });
    return new FormGroup<RoomFormGroupContent>({
      id: new FormControl(
        { value: roomRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numberOfPeople: new FormControl(roomRawValue.numberOfPeople),
      price: new FormControl(roomRawValue.price),
      periodType: new FormControl(roomRawValue.periodType),
      checkInDate: new FormControl(roomRawValue.checkInDate),
      checkOutDate: new FormControl(roomRawValue.checkOutDate),
      createdAt: new FormControl(roomRawValue.createdAt),
      updatedAt: new FormControl(roomRawValue.updatedAt),
      hotelResult: new FormControl(roomRawValue.hotelResult),
    });
  }

  getRoom(form: RoomFormGroup): IRoom | NewRoom {
    return this.convertRoomRawValueToRoom(form.getRawValue() as RoomFormRawValue | NewRoomFormRawValue);
  }

  resetForm(form: RoomFormGroup, room: RoomFormGroupInput): void {
    const roomRawValue = this.convertRoomToRoomRawValue({ ...this.getFormDefaults(), ...room });
    form.reset(
      {
        ...roomRawValue,
        id: { value: roomRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RoomFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertRoomRawValueToRoom(rawRoom: RoomFormRawValue | NewRoomFormRawValue): IRoom | NewRoom {
    return {
      ...rawRoom,
      createdAt: dayjs(rawRoom.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawRoom.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertRoomToRoomRawValue(
    room: IRoom | (Partial<NewRoom> & RoomFormDefaults)
  ): RoomFormRawValue | PartialWithRequiredKeyOf<NewRoomFormRawValue> {
    return {
      ...room,
      createdAt: room.createdAt ? room.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: room.updatedAt ? room.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
