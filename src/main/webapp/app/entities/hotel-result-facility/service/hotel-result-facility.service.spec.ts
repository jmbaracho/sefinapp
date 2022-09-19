import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHotelResultFacility } from '../hotel-result-facility.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../hotel-result-facility.test-samples';

import { HotelResultFacilityService, RestHotelResultFacility } from './hotel-result-facility.service';

const requireRestSample: RestHotelResultFacility = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('HotelResultFacility Service', () => {
  let service: HotelResultFacilityService;
  let httpMock: HttpTestingController;
  let expectedResult: IHotelResultFacility | IHotelResultFacility[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HotelResultFacilityService);
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

    it('should create a HotelResultFacility', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const hotelResultFacility = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hotelResultFacility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HotelResultFacility', () => {
      const hotelResultFacility = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hotelResultFacility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HotelResultFacility', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HotelResultFacility', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HotelResultFacility', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHotelResultFacilityToCollectionIfMissing', () => {
      it('should add a HotelResultFacility to an empty array', () => {
        const hotelResultFacility: IHotelResultFacility = sampleWithRequiredData;
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing([], hotelResultFacility);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotelResultFacility);
      });

      it('should not add a HotelResultFacility to an array that contains it', () => {
        const hotelResultFacility: IHotelResultFacility = sampleWithRequiredData;
        const hotelResultFacilityCollection: IHotelResultFacility[] = [
          {
            ...hotelResultFacility,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing(hotelResultFacilityCollection, hotelResultFacility);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HotelResultFacility to an array that doesn't contain it", () => {
        const hotelResultFacility: IHotelResultFacility = sampleWithRequiredData;
        const hotelResultFacilityCollection: IHotelResultFacility[] = [sampleWithPartialData];
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing(hotelResultFacilityCollection, hotelResultFacility);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotelResultFacility);
      });

      it('should add only unique HotelResultFacility to an array', () => {
        const hotelResultFacilityArray: IHotelResultFacility[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hotelResultFacilityCollection: IHotelResultFacility[] = [sampleWithRequiredData];
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing(hotelResultFacilityCollection, ...hotelResultFacilityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hotelResultFacility: IHotelResultFacility = sampleWithRequiredData;
        const hotelResultFacility2: IHotelResultFacility = sampleWithPartialData;
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing([], hotelResultFacility, hotelResultFacility2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotelResultFacility);
        expect(expectedResult).toContain(hotelResultFacility2);
      });

      it('should accept null and undefined values', () => {
        const hotelResultFacility: IHotelResultFacility = sampleWithRequiredData;
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing([], null, hotelResultFacility, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotelResultFacility);
      });

      it('should return initial array if no HotelResultFacility is added', () => {
        const hotelResultFacilityCollection: IHotelResultFacility[] = [sampleWithRequiredData];
        expectedResult = service.addHotelResultFacilityToCollectionIfMissing(hotelResultFacilityCollection, undefined, null);
        expect(expectedResult).toEqual(hotelResultFacilityCollection);
      });
    });

    describe('compareHotelResultFacility', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHotelResultFacility(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHotelResultFacility(entity1, entity2);
        const compareResult2 = service.compareHotelResultFacility(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHotelResultFacility(entity1, entity2);
        const compareResult2 = service.compareHotelResultFacility(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHotelResultFacility(entity1, entity2);
        const compareResult2 = service.compareHotelResultFacility(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
