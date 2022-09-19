import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../schedule-execution.test-samples';

import { ScheduleExecutionFormService } from './schedule-execution-form.service';

describe('ScheduleExecution Form Service', () => {
  let service: ScheduleExecutionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScheduleExecutionFormService);
  });

  describe('Service methods', () => {
    describe('createScheduleExecutionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createScheduleExecutionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            schedule: expect.any(Object),
          })
        );
      });

      it('passing IScheduleExecution should create a new form with FormGroup', () => {
        const formGroup = service.createScheduleExecutionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            schedule: expect.any(Object),
          })
        );
      });
    });

    describe('getScheduleExecution', () => {
      it('should return NewScheduleExecution for default ScheduleExecution initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createScheduleExecutionFormGroup(sampleWithNewData);

        const scheduleExecution = service.getScheduleExecution(formGroup) as any;

        expect(scheduleExecution).toMatchObject(sampleWithNewData);
      });

      it('should return NewScheduleExecution for empty ScheduleExecution initial value', () => {
        const formGroup = service.createScheduleExecutionFormGroup();

        const scheduleExecution = service.getScheduleExecution(formGroup) as any;

        expect(scheduleExecution).toMatchObject({});
      });

      it('should return IScheduleExecution', () => {
        const formGroup = service.createScheduleExecutionFormGroup(sampleWithRequiredData);

        const scheduleExecution = service.getScheduleExecution(formGroup) as any;

        expect(scheduleExecution).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IScheduleExecution should not enable id FormControl', () => {
        const formGroup = service.createScheduleExecutionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewScheduleExecution should disable id FormControl', () => {
        const formGroup = service.createScheduleExecutionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
