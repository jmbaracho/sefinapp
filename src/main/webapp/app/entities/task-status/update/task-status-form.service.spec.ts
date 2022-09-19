import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../task-status.test-samples';

import { TaskStatusFormService } from './task-status-form.service';

describe('TaskStatus Form Service', () => {
  let service: TaskStatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskStatusFormService);
  });

  describe('Service methods', () => {
    describe('createTaskStatusFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTaskStatusFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          })
        );
      });

      it('passing ITaskStatus should create a new form with FormGroup', () => {
        const formGroup = service.createTaskStatusFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          })
        );
      });
    });

    describe('getTaskStatus', () => {
      it('should return NewTaskStatus for default TaskStatus initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTaskStatusFormGroup(sampleWithNewData);

        const taskStatus = service.getTaskStatus(formGroup) as any;

        expect(taskStatus).toMatchObject(sampleWithNewData);
      });

      it('should return NewTaskStatus for empty TaskStatus initial value', () => {
        const formGroup = service.createTaskStatusFormGroup();

        const taskStatus = service.getTaskStatus(formGroup) as any;

        expect(taskStatus).toMatchObject({});
      });

      it('should return ITaskStatus', () => {
        const formGroup = service.createTaskStatusFormGroup(sampleWithRequiredData);

        const taskStatus = service.getTaskStatus(formGroup) as any;

        expect(taskStatus).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITaskStatus should not enable id FormControl', () => {
        const formGroup = service.createTaskStatusFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTaskStatus should disable id FormControl', () => {
        const formGroup = service.createTaskStatusFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
