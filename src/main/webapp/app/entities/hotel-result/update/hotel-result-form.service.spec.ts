import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hotel-result.test-samples';

import { HotelResultFormService } from './hotel-result-form.service';

describe('HotelResult Form Service', () => {
  let service: HotelResultFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelResultFormService);
  });

  describe('Service methods', () => {
    describe('createHotelResultFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHotelResultFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            starsCount: expect.any(Object),
            rating: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            task: expect.any(Object),
            agent: expect.any(Object),
            company: expect.any(Object),
          })
        );
      });

      it('passing IHotelResult should create a new form with FormGroup', () => {
        const formGroup = service.createHotelResultFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            starsCount: expect.any(Object),
            rating: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            task: expect.any(Object),
            agent: expect.any(Object),
            company: expect.any(Object),
          })
        );
      });
    });

    describe('getHotelResult', () => {
      it('should return NewHotelResult for default HotelResult initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHotelResultFormGroup(sampleWithNewData);

        const hotelResult = service.getHotelResult(formGroup) as any;

        expect(hotelResult).toMatchObject(sampleWithNewData);
      });

      it('should return NewHotelResult for empty HotelResult initial value', () => {
        const formGroup = service.createHotelResultFormGroup();

        const hotelResult = service.getHotelResult(formGroup) as any;

        expect(hotelResult).toMatchObject({});
      });

      it('should return IHotelResult', () => {
        const formGroup = service.createHotelResultFormGroup(sampleWithRequiredData);

        const hotelResult = service.getHotelResult(formGroup) as any;

        expect(hotelResult).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHotelResult should not enable id FormControl', () => {
        const formGroup = service.createHotelResultFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHotelResult should disable id FormControl', () => {
        const formGroup = service.createHotelResultFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
