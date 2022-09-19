import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IScheduleExecution } from '../schedule-execution.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../schedule-execution.test-samples';

import { ScheduleExecutionService, RestScheduleExecution } from './schedule-execution.service';

const requireRestSample: RestScheduleExecution = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('ScheduleExecution Service', () => {
  let service: ScheduleExecutionService;
  let httpMock: HttpTestingController;
  let expectedResult: IScheduleExecution | IScheduleExecution[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ScheduleExecutionService);
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

    it('should create a ScheduleExecution', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const scheduleExecution = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(scheduleExecution).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ScheduleExecution', () => {
      const scheduleExecution = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(scheduleExecution).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ScheduleExecution', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ScheduleExecution', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ScheduleExecution', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addScheduleExecutionToCollectionIfMissing', () => {
      it('should add a ScheduleExecution to an empty array', () => {
        const scheduleExecution: IScheduleExecution = sampleWithRequiredData;
        expectedResult = service.addScheduleExecutionToCollectionIfMissing([], scheduleExecution);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scheduleExecution);
      });

      it('should not add a ScheduleExecution to an array that contains it', () => {
        const scheduleExecution: IScheduleExecution = sampleWithRequiredData;
        const scheduleExecutionCollection: IScheduleExecution[] = [
          {
            ...scheduleExecution,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addScheduleExecutionToCollectionIfMissing(scheduleExecutionCollection, scheduleExecution);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ScheduleExecution to an array that doesn't contain it", () => {
        const scheduleExecution: IScheduleExecution = sampleWithRequiredData;
        const scheduleExecutionCollection: IScheduleExecution[] = [sampleWithPartialData];
        expectedResult = service.addScheduleExecutionToCollectionIfMissing(scheduleExecutionCollection, scheduleExecution);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scheduleExecution);
      });

      it('should add only unique ScheduleExecution to an array', () => {
        const scheduleExecutionArray: IScheduleExecution[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const scheduleExecutionCollection: IScheduleExecution[] = [sampleWithRequiredData];
        expectedResult = service.addScheduleExecutionToCollectionIfMissing(scheduleExecutionCollection, ...scheduleExecutionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const scheduleExecution: IScheduleExecution = sampleWithRequiredData;
        const scheduleExecution2: IScheduleExecution = sampleWithPartialData;
        expectedResult = service.addScheduleExecutionToCollectionIfMissing([], scheduleExecution, scheduleExecution2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scheduleExecution);
        expect(expectedResult).toContain(scheduleExecution2);
      });

      it('should accept null and undefined values', () => {
        const scheduleExecution: IScheduleExecution = sampleWithRequiredData;
        expectedResult = service.addScheduleExecutionToCollectionIfMissing([], null, scheduleExecution, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scheduleExecution);
      });

      it('should return initial array if no ScheduleExecution is added', () => {
        const scheduleExecutionCollection: IScheduleExecution[] = [sampleWithRequiredData];
        expectedResult = service.addScheduleExecutionToCollectionIfMissing(scheduleExecutionCollection, undefined, null);
        expect(expectedResult).toEqual(scheduleExecutionCollection);
      });
    });

    describe('compareScheduleExecution', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareScheduleExecution(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareScheduleExecution(entity1, entity2);
        const compareResult2 = service.compareScheduleExecution(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareScheduleExecution(entity1, entity2);
        const compareResult2 = service.compareScheduleExecution(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareScheduleExecution(entity1, entity2);
        const compareResult2 = service.compareScheduleExecution(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
