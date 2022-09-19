import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HotelResultFacilityFormService } from './hotel-result-facility-form.service';
import { HotelResultFacilityService } from '../service/hotel-result-facility.service';
import { IHotelResultFacility } from '../hotel-result-facility.model';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';
import { HotelResultService } from 'app/entities/hotel-result/service/hotel-result.service';
import { IFacility } from 'app/entities/facility/facility.model';
import { FacilityService } from 'app/entities/facility/service/facility.service';

import { HotelResultFacilityUpdateComponent } from './hotel-result-facility-update.component';

describe('HotelResultFacility Management Update Component', () => {
  let comp: HotelResultFacilityUpdateComponent;
  let fixture: ComponentFixture<HotelResultFacilityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelResultFacilityFormService: HotelResultFacilityFormService;
  let hotelResultFacilityService: HotelResultFacilityService;
  let hotelResultService: HotelResultService;
  let facilityService: FacilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HotelResultFacilityUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HotelResultFacilityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelResultFacilityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelResultFacilityFormService = TestBed.inject(HotelResultFacilityFormService);
    hotelResultFacilityService = TestBed.inject(HotelResultFacilityService);
    hotelResultService = TestBed.inject(HotelResultService);
    facilityService = TestBed.inject(FacilityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call HotelResult query and add missing value', () => {
      const hotelResultFacility: IHotelResultFacility = { id: 456 };
      const hotelResult: IHotelResult = { id: 84087 };
      hotelResultFacility.hotelResult = hotelResult;

      const hotelResultCollection: IHotelResult[] = [{ id: 62497 }];
      jest.spyOn(hotelResultService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelResultCollection })));
      const additionalHotelResults = [hotelResult];
      const expectedCollection: IHotelResult[] = [...additionalHotelResults, ...hotelResultCollection];
      jest.spyOn(hotelResultService, 'addHotelResultToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelResultFacility });
      comp.ngOnInit();

      expect(hotelResultService.query).toHaveBeenCalled();
      expect(hotelResultService.addHotelResultToCollectionIfMissing).toHaveBeenCalledWith(
        hotelResultCollection,
        ...additionalHotelResults.map(expect.objectContaining)
      );
      expect(comp.hotelResultsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Facility query and add missing value', () => {
      const hotelResultFacility: IHotelResultFacility = { id: 456 };
      const facility: IFacility = { id: 61664 };
      hotelResultFacility.facility = facility;

      const facilityCollection: IFacility[] = [{ id: 79971 }];
      jest.spyOn(facilityService, 'query').mockReturnValue(of(new HttpResponse({ body: facilityCollection })));
      const additionalFacilities = [facility];
      const expectedCollection: IFacility[] = [...additionalFacilities, ...facilityCollection];
      jest.spyOn(facilityService, 'addFacilityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelResultFacility });
      comp.ngOnInit();

      expect(facilityService.query).toHaveBeenCalled();
      expect(facilityService.addFacilityToCollectionIfMissing).toHaveBeenCalledWith(
        facilityCollection,
        ...additionalFacilities.map(expect.objectContaining)
      );
      expect(comp.facilitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hotelResultFacility: IHotelResultFacility = { id: 456 };
      const hotelResult: IHotelResult = { id: 64538 };
      hotelResultFacility.hotelResult = hotelResult;
      const facility: IFacility = { id: 30575 };
      hotelResultFacility.facility = facility;

      activatedRoute.data = of({ hotelResultFacility });
      comp.ngOnInit();

      expect(comp.hotelResultsSharedCollection).toContain(hotelResult);
      expect(comp.facilitiesSharedCollection).toContain(facility);
      expect(comp.hotelResultFacility).toEqual(hotelResultFacility);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResultFacility>>();
      const hotelResultFacility = { id: 123 };
      jest.spyOn(hotelResultFacilityFormService, 'getHotelResultFacility').mockReturnValue(hotelResultFacility);
      jest.spyOn(hotelResultFacilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResultFacility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelResultFacility }));
      saveSubject.complete();

      // THEN
      expect(hotelResultFacilityFormService.getHotelResultFacility).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelResultFacilityService.update).toHaveBeenCalledWith(expect.objectContaining(hotelResultFacility));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResultFacility>>();
      const hotelResultFacility = { id: 123 };
      jest.spyOn(hotelResultFacilityFormService, 'getHotelResultFacility').mockReturnValue({ id: null });
      jest.spyOn(hotelResultFacilityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResultFacility: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelResultFacility }));
      saveSubject.complete();

      // THEN
      expect(hotelResultFacilityFormService.getHotelResultFacility).toHaveBeenCalled();
      expect(hotelResultFacilityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelResultFacility>>();
      const hotelResultFacility = { id: 123 };
      jest.spyOn(hotelResultFacilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelResultFacility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelResultFacilityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHotelResult', () => {
      it('Should forward to hotelResultService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelResultService, 'compareHotelResult');
        comp.compareHotelResult(entity, entity2);
        expect(hotelResultService.compareHotelResult).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFacility', () => {
      it('Should forward to facilityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facilityService, 'compareFacility');
        comp.compareFacility(entity, entity2);
        expect(facilityService.compareFacility).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
