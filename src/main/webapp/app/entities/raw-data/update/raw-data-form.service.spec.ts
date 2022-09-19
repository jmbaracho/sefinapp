import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../raw-data.test-samples';

import { RawDataFormService } from './raw-data-form.service';

describe('RawData Form Service', () => {
  let service: RawDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RawDataFormService);
  });

  describe('Service methods', () => {
    describe('createRawDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRawDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            content: expect.any(Object),
            processed: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            task: expect.any(Object),
          })
        );
      });

      it('passing IRawData should create a new form with FormGroup', () => {
        const formGroup = service.createRawDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            content: expect.any(Object),
            processed: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            task: expect.any(Object),
          })
        );
      });
    });

    describe('getRawData', () => {
      it('should return NewRawData for default RawData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRawDataFormGroup(sampleWithNewData);

        const rawData = service.getRawData(formGroup) as any;

        expect(rawData).toMatchObject(sampleWithNewData);
      });

      it('should return NewRawData for empty RawData initial value', () => {
        const formGroup = service.createRawDataFormGroup();

        const rawData = service.getRawData(formGroup) as any;

        expect(rawData).toMatchObject({});
      });

      it('should return IRawData', () => {
        const formGroup = service.createRawDataFormGroup(sampleWithRequiredData);

        const rawData = service.getRawData(formGroup) as any;

        expect(rawData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRawData should not enable id FormControl', () => {
        const formGroup = service.createRawDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRawData should disable id FormControl', () => {
        const formGroup = service.createRawDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
