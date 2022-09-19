import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RoomFormService, RoomFormGroup } from './room-form.service';
import { IRoom } from '../room.model';
import { RoomService } from '../service/room.service';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';
import { HotelResultService } from 'app/entities/hotel-result/service/hotel-result.service';

@Component({
  selector: 'jhi-room-update',
  templateUrl: './room-update.component.html',
})
export class RoomUpdateComponent implements OnInit {
  isSaving = false;
  room: IRoom | null = null;

  hotelResultsSharedCollection: IHotelResult[] = [];

  editForm: RoomFormGroup = this.roomFormService.createRoomFormGroup();

  constructor(
    protected roomService: RoomService,
    protected roomFormService: RoomFormService,
    protected hotelResultService: HotelResultService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareHotelResult = (o1: IHotelResult | null, o2: IHotelResult | null): boolean => this.hotelResultService.compareHotelResult(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ room }) => {
      this.room = room;
      if (room) {
        this.updateForm(room);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const room = this.roomFormService.getRoom(this.editForm);
    if (room.id !== null) {
      this.subscribeToSaveResponse(this.roomService.update(room));
    } else {
      this.subscribeToSaveResponse(this.roomService.create(room));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(room: IRoom): void {
    this.room = room;
    this.roomFormService.resetForm(this.editForm, room);

    this.hotelResultsSharedCollection = this.hotelResultService.addHotelResultToCollectionIfMissing<IHotelResult>(
      this.hotelResultsSharedCollection,
      room.hotelResult
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelResultService
      .query()
      .pipe(map((res: HttpResponse<IHotelResult[]>) => res.body ?? []))
      .pipe(
        map((hotelResults: IHotelResult[]) =>
          this.hotelResultService.addHotelResultToCollectionIfMissing<IHotelResult>(hotelResults, this.room?.hotelResult)
        )
      )
      .subscribe((hotelResults: IHotelResult[]) => (this.hotelResultsSharedCollection = hotelResults));
  }
}
