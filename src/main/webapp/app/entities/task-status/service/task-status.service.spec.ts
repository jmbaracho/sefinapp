import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITaskStatus } from '../task-status.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../task-status.test-samples';

import { TaskStatusService, RestTaskStatus } from './task-status.service';

const requireRestSample: RestTaskStatus = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('TaskStatus Service', () => {
  let service: TaskStatusService;
  let httpMock: HttpTestingController;
  let expectedResult: ITaskStatus | ITaskStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TaskStatusService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TaskStatus', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const taskStatus = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(taskStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TaskStatus', () => {
      const taskStatus = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(taskStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TaskStatus', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TaskStatus', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TaskStatus', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTaskStatusToCollectionIfMissing', () => {
      it('should add a TaskStatus to an empty array', () => {
        const taskStatus: ITaskStatus = sampleWithRequiredData;
        expectedResult = service.addTaskStatusToCollectionIfMissing([], taskStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taskStatus);
      });

      it('should not add a TaskStatus to an array that contains it', () => {
        const taskStatus: ITaskStatus = sampleWithRequiredData;
        const taskStatusCollection: ITaskStatus[] = [
          {
            ...taskStatus,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTaskStatusToCollectionIfMissing(taskStatusCollection, taskStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TaskStatus to an array that doesn't contain it", () => {
        const taskStatus: ITaskStatus = sampleWithRequiredData;
        const taskStatusCollection: ITaskStatus[] = [sampleWithPartialData];
        expectedResult = service.addTaskStatusToCollectionIfMissing(taskStatusCollection, taskStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taskStatus);
      });

      it('should add only unique TaskStatus to an array', () => {
        const taskStatusArray: ITaskStatus[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const taskStatusCollection: ITaskStatus[] = [sampleWithRequiredData];
        expectedResult = service.addTaskStatusToCollectionIfMissing(taskStatusCollection, ...taskStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taskStatus: ITaskStatus = sampleWithRequiredData;
        const taskStatus2: ITaskStatus = sampleWithPartialData;
        expectedResult = service.addTaskStatusToCollectionIfMissing([], taskStatus, taskStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taskStatus);
        expect(expectedResult).toContain(taskStatus2);
      });

      it('should accept null and undefined values', () => {
        const taskStatus: ITaskStatus = sampleWithRequiredData;
        expectedResult = service.addTaskStatusToCollectionIfMissing([], null, taskStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taskStatus);
      });

      it('should return initial array if no TaskStatus is added', () => {
        const taskStatusCollection: ITaskStatus[] = [sampleWithRequiredData];
        expectedResult = service.addTaskStatusToCollectionIfMissing(taskStatusCollection, undefined, null);
        expect(expectedResult).toEqual(taskStatusCollection);
      });
    });

    describe('compareTaskStatus', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTaskStatus(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTaskStatus(entity1, entity2);
        const compareResult2 = service.compareTaskStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTaskStatus(entity1, entity2);
        const compareResult2 = service.compareTaskStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTaskStatus(entity1, entity2);
        const compareResult2 = service.compareTaskStatus(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
