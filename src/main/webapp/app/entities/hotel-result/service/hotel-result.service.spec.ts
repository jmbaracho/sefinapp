import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHotelResult } from '../hotel-result.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../hotel-result.test-samples';

import { HotelResultService, RestHotelResult } from './hotel-result.service';

const requireRestSample: RestHotelResult = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('HotelResult Service', () => {
  let service: HotelResultService;
  let httpMock: HttpTestingController;
  let expectedResult: IHotelResult | IHotelResult[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HotelResultService);
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

    it('should create a HotelResult', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const hotelResult = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hotelResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HotelResult', () => {
      const hotelResult = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hotelResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HotelResult', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HotelResult', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HotelResult', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHotelResultToCollectionIfMissing', () => {
      it('should add a HotelResult to an empty array', () => {
        const hotelResult: IHotelResult = sampleWithRequiredData;
        expectedResult = service.addHotelResultToCollectionIfMissing([], hotelResult);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotelResult);
      });

      it('should not add a HotelResult to an array that contains it', () => {
        const hotelResult: IHotelResult = sampleWithRequiredData;
        const hotelResultCollection: IHotelResult[] = [
          {
            ...hotelResult,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHotelResultToCollectionIfMissing(hotelResultCollection, hotelResult);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HotelResult to an array that doesn't contain it", () => {
        const hotelResult: IHotelResult = sampleWithRequiredData;
        const hotelResultCollection: IHotelResult[] = [sampleWithPartialData];
        expectedResult = service.addHotelResultToCollectionIfMissing(hotelResultCollection, hotelResult);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotelResult);
      });

      it('should add only unique HotelResult to an array', () => {
        const hotelResultArray: IHotelResult[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hotelResultCollection: IHotelResult[] = [sampleWithRequiredData];
        expectedResult = service.addHotelResultToCollectionIfMissing(hotelResultCollection, ...hotelResultArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hotelResult: IHotelResult = sampleWithRequiredData;
        const hotelResult2: IHotelResult = sampleWithPartialData;
        expectedResult = service.addHotelResultToCollectionIfMissing([], hotelResult, hotelResult2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotelResult);
        expect(expectedResult).toContain(hotelResult2);
      });

      it('should accept null and undefined values', () => {
        const hotelResult: IHotelResult = sampleWithRequiredData;
        expectedResult = service.addHotelResultToCollectionIfMissing([], null, hotelResult, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotelResult);
      });

      it('should return initial array if no HotelResult is added', () => {
        const hotelResultCollection: IHotelResult[] = [sampleWithRequiredData];
        expectedResult = service.addHotelResultToCollectionIfMissing(hotelResultCollection, undefined, null);
        expect(expectedResult).toEqual(hotelResultCollection);
      });
    });

    describe('compareHotelResult', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHotelResult(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHotelResult(entity1, entity2);
        const compareResult2 = service.compareHotelResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHotelResult(entity1, entity2);
        const compareResult2 = service.compareHotelResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHotelResult(entity1, entity2);
        const compareResult2 = service.compareHotelResult(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
