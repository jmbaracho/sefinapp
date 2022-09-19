import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hotel-result-facility.test-samples';

import { HotelResultFacilityFormService } from './hotel-result-facility-form.service';

describe('HotelResultFacility Form Service', () => {
  let service: HotelResultFacilityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelResultFacilityFormService);
  });

  describe('Service methods', () => {
    describe('createHotelResultFacilityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHotelResultFacilityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            hotelResult: expect.any(Object),
            facility: expect.any(Object),
          })
        );
      });

      it('passing IHotelResultFacility should create a new form with FormGroup', () => {
        const formGroup = service.createHotelResultFacilityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            hotelResult: expect.any(Object),
            facility: expect.any(Object),
          })
        );
      });
    });

    describe('getHotelResultFacility', () => {
      it('should return NewHotelResultFacility for default HotelResultFacility initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHotelResultFacilityFormGroup(sampleWithNewData);

        const hotelResultFacility = service.getHotelResultFacility(formGroup) as any;

        expect(hotelResultFacility).toMatchObject(sampleWithNewData);
      });

      it('should return NewHotelResultFacility for empty HotelResultFacility initial value', () => {
        const formGroup = service.createHotelResultFacilityFormGroup();

        const hotelResultFacility = service.getHotelResultFacility(formGroup) as any;

        expect(hotelResultFacility).toMatchObject({});
      });

      it('should return IHotelResultFacility', () => {
        const formGroup = service.createHotelResultFacilityFormGroup(sampleWithRequiredData);

        const hotelResultFacility = service.getHotelResultFacility(formGroup) as any;

        expect(hotelResultFacility).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHotelResultFacility should not enable id FormControl', () => {
        const formGroup = service.createHotelResultFacilityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHotelResultFacility should disable id FormControl', () => {
        const formGroup = service.createHotelResultFacilityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
