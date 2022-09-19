import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INfse } from '../nfse.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nfse.test-samples';

import { NfseService } from './nfse.service';

const requireRestSample: INfse = {
  ...sampleWithRequiredData,
};

describe('Nfse Service', () => {
  let service: NfseService;
  let httpMock: HttpTestingController;
  let expectedResult: INfse | INfse[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NfseService);
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

    it('should create a Nfse', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nfse = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nfse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Nfse', () => {
      const nfse = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nfse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Nfse', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Nfse', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Nfse', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNfseToCollectionIfMissing', () => {
      it('should add a Nfse to an empty array', () => {
        const nfse: INfse = sampleWithRequiredData;
        expectedResult = service.addNfseToCollectionIfMissing([], nfse);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nfse);
      });

      it('should not add a Nfse to an array that contains it', () => {
        const nfse: INfse = sampleWithRequiredData;
        const nfseCollection: INfse[] = [
          {
            ...nfse,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNfseToCollectionIfMissing(nfseCollection, nfse);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Nfse to an array that doesn't contain it", () => {
        const nfse: INfse = sampleWithRequiredData;
        const nfseCollection: INfse[] = [sampleWithPartialData];
        expectedResult = service.addNfseToCollectionIfMissing(nfseCollection, nfse);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nfse);
      });

      it('should add only unique Nfse to an array', () => {
        const nfseArray: INfse[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nfseCollection: INfse[] = [sampleWithRequiredData];
        expectedResult = service.addNfseToCollectionIfMissing(nfseCollection, ...nfseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nfse: INfse = sampleWithRequiredData;
        const nfse2: INfse = sampleWithPartialData;
        expectedResult = service.addNfseToCollectionIfMissing([], nfse, nfse2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nfse);
        expect(expectedResult).toContain(nfse2);
      });

      it('should accept null and undefined values', () => {
        const nfse: INfse = sampleWithRequiredData;
        expectedResult = service.addNfseToCollectionIfMissing([], null, nfse, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nfse);
      });

      it('should return initial array if no Nfse is added', () => {
        const nfseCollection: INfse[] = [sampleWithRequiredData];
        expectedResult = service.addNfseToCollectionIfMissing(nfseCollection, undefined, null);
        expect(expectedResult).toEqual(nfseCollection);
      });
    });

    describe('compareNfse', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNfse(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNfse(entity1, entity2);
        const compareResult2 = service.compareNfse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNfse(entity1, entity2);
        const compareResult2 = service.compareNfse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNfse(entity1, entity2);
        const compareResult2 = service.compareNfse(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
