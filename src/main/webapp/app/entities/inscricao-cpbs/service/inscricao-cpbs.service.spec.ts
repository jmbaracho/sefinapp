import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInscricaoCPBS } from '../inscricao-cpbs.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../inscricao-cpbs.test-samples';

import { InscricaoCPBSService } from './inscricao-cpbs.service';

const requireRestSample: IInscricaoCPBS = {
  ...sampleWithRequiredData,
};

describe('InscricaoCPBS Service', () => {
  let service: InscricaoCPBSService;
  let httpMock: HttpTestingController;
  let expectedResult: IInscricaoCPBS | IInscricaoCPBS[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InscricaoCPBSService);
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

    it('should create a InscricaoCPBS', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const inscricaoCPBS = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(inscricaoCPBS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InscricaoCPBS', () => {
      const inscricaoCPBS = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(inscricaoCPBS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InscricaoCPBS', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InscricaoCPBS', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InscricaoCPBS', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInscricaoCPBSToCollectionIfMissing', () => {
      it('should add a InscricaoCPBS to an empty array', () => {
        const inscricaoCPBS: IInscricaoCPBS = sampleWithRequiredData;
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing([], inscricaoCPBS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inscricaoCPBS);
      });

      it('should not add a InscricaoCPBS to an array that contains it', () => {
        const inscricaoCPBS: IInscricaoCPBS = sampleWithRequiredData;
        const inscricaoCPBSCollection: IInscricaoCPBS[] = [
          {
            ...inscricaoCPBS,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing(inscricaoCPBSCollection, inscricaoCPBS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InscricaoCPBS to an array that doesn't contain it", () => {
        const inscricaoCPBS: IInscricaoCPBS = sampleWithRequiredData;
        const inscricaoCPBSCollection: IInscricaoCPBS[] = [sampleWithPartialData];
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing(inscricaoCPBSCollection, inscricaoCPBS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inscricaoCPBS);
      });

      it('should add only unique InscricaoCPBS to an array', () => {
        const inscricaoCPBSArray: IInscricaoCPBS[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const inscricaoCPBSCollection: IInscricaoCPBS[] = [sampleWithRequiredData];
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing(inscricaoCPBSCollection, ...inscricaoCPBSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inscricaoCPBS: IInscricaoCPBS = sampleWithRequiredData;
        const inscricaoCPBS2: IInscricaoCPBS = sampleWithPartialData;
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing([], inscricaoCPBS, inscricaoCPBS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inscricaoCPBS);
        expect(expectedResult).toContain(inscricaoCPBS2);
      });

      it('should accept null and undefined values', () => {
        const inscricaoCPBS: IInscricaoCPBS = sampleWithRequiredData;
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing([], null, inscricaoCPBS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inscricaoCPBS);
      });

      it('should return initial array if no InscricaoCPBS is added', () => {
        const inscricaoCPBSCollection: IInscricaoCPBS[] = [sampleWithRequiredData];
        expectedResult = service.addInscricaoCPBSToCollectionIfMissing(inscricaoCPBSCollection, undefined, null);
        expect(expectedResult).toEqual(inscricaoCPBSCollection);
      });
    });

    describe('compareInscricaoCPBS', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInscricaoCPBS(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInscricaoCPBS(entity1, entity2);
        const compareResult2 = service.compareInscricaoCPBS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInscricaoCPBS(entity1, entity2);
        const compareResult2 = service.compareInscricaoCPBS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInscricaoCPBS(entity1, entity2);
        const compareResult2 = service.compareInscricaoCPBS(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
