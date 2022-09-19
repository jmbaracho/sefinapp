import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HotelResultFacilityFormService, HotelResultFacilityFormGroup } from './hotel-result-facility-form.service';
import { IHotelResultFacility } from '../hotel-result-facility.model';
import { HotelResultFacilityService } from '../service/hotel-result-facility.service';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';
import { HotelResultService } from 'app/entities/hotel-result/service/hotel-result.service';
import { IFacility } from 'app/entities/facility/facility.model';
import { FacilityService } from 'app/entities/facility/service/facility.service';

@Component({
  selector: 'jhi-hotel-result-facility-update',
  templateUrl: './hotel-result-facility-update.component.html',
})
export class HotelResultFacilityUpdateComponent implements OnInit {
  isSaving = false;
  hotelResultFacility: IHotelResultFacility | null = null;

  hotelResultsSharedCollection: IHotelResult[] = [];
  facilitiesSharedCollection: IFacility[] = [];

  editForm: HotelResultFacilityFormGroup = this.hotelResultFacilityFormService.createHotelResultFacilityFormGroup();

  constructor(
    protected hotelResultFacilityService: HotelResultFacilityService,
    protected hotelResultFacilityFormService: HotelResultFacilityFormService,
    protected hotelResultService: HotelResultService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareHotelResult = (o1: IHotelResult | null, o2: IHotelResult | null): boolean => this.hotelResultService.compareHotelResult(o1, o2);

  compareFacility = (o1: IFacility | null, o2: IFacility | null): boolean => this.facilityService.compareFacility(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelResultFacility }) => {
      this.hotelResultFacility = hotelResultFacility;
      if (hotelResultFacility) {
        this.updateForm(hotelResultFacility);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelResultFacility = this.hotelResultFacilityFormService.getHotelResultFacility(this.editForm);
    if (hotelResultFacility.id !== null) {
      this.subscribeToSaveResponse(this.hotelResultFacilityService.update(hotelResultFacility));
    } else {
      this.subscribeToSaveResponse(this.hotelResultFacilityService.create(hotelResultFacility));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelResultFacility>>): void {
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

  protected updateForm(hotelResultFacility: IHotelResultFacility): void {
    this.hotelResultFacility = hotelResultFacility;
    this.hotelResultFacilityFormService.resetForm(this.editForm, hotelResultFacility);

    this.hotelResultsSharedCollection = this.hotelResultService.addHotelResultToCollectionIfMissing<IHotelResult>(
      this.hotelResultsSharedCollection,
      hotelResultFacility.hotelResult
    );
    this.facilitiesSharedCollection = this.facilityService.addFacilityToCollectionIfMissing<IFacility>(
      this.facilitiesSharedCollection,
      hotelResultFacility.facility
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelResultService
      .query()
      .pipe(map((res: HttpResponse<IHotelResult[]>) => res.body ?? []))
      .pipe(
        map((hotelResults: IHotelResult[]) =>
          this.hotelResultService.addHotelResultToCollectionIfMissing<IHotelResult>(hotelResults, this.hotelResultFacility?.hotelResult)
        )
      )
      .subscribe((hotelResults: IHotelResult[]) => (this.hotelResultsSharedCollection = hotelResults));

    this.facilityService
      .query()
      .pipe(map((res: HttpResponse<IFacility[]>) => res.body ?? []))
      .pipe(
        map((facilities: IFacility[]) =>
          this.facilityService.addFacilityToCollectionIfMissing<IFacility>(facilities, this.hotelResultFacility?.facility)
        )
      )
      .subscribe((facilities: IFacility[]) => (this.facilitiesSharedCollection = facilities));
  }
}
