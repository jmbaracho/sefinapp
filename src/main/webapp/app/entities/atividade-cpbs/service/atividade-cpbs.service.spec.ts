import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAtividadeCPBS } from '../atividade-cpbs.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../atividade-cpbs.test-samples';

import { AtividadeCPBSService } from './atividade-cpbs.service';

const requireRestSample: IAtividadeCPBS = {
  ...sampleWithRequiredData,
};

describe('AtividadeCPBS Service', () => {
  let service: AtividadeCPBSService;
  let httpMock: HttpTestingController;
  let expectedResult: IAtividadeCPBS | IAtividadeCPBS[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AtividadeCPBSService);
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

    it('should create a AtividadeCPBS', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const atividadeCPBS = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(atividadeCPBS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AtividadeCPBS', () => {
      const atividadeCPBS = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(atividadeCPBS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AtividadeCPBS', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AtividadeCPBS', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AtividadeCPBS', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAtividadeCPBSToCollectionIfMissing', () => {
      it('should add a AtividadeCPBS to an empty array', () => {
        const atividadeCPBS: IAtividadeCPBS = sampleWithRequiredData;
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing([], atividadeCPBS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atividadeCPBS);
      });

      it('should not add a AtividadeCPBS to an array that contains it', () => {
        const atividadeCPBS: IAtividadeCPBS = sampleWithRequiredData;
        const atividadeCPBSCollection: IAtividadeCPBS[] = [
          {
            ...atividadeCPBS,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing(atividadeCPBSCollection, atividadeCPBS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AtividadeCPBS to an array that doesn't contain it", () => {
        const atividadeCPBS: IAtividadeCPBS = sampleWithRequiredData;
        const atividadeCPBSCollection: IAtividadeCPBS[] = [sampleWithPartialData];
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing(atividadeCPBSCollection, atividadeCPBS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atividadeCPBS);
      });

      it('should add only unique AtividadeCPBS to an array', () => {
        const atividadeCPBSArray: IAtividadeCPBS[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const atividadeCPBSCollection: IAtividadeCPBS[] = [sampleWithRequiredData];
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing(atividadeCPBSCollection, ...atividadeCPBSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const atividadeCPBS: IAtividadeCPBS = sampleWithRequiredData;
        const atividadeCPBS2: IAtividadeCPBS = sampleWithPartialData;
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing([], atividadeCPBS, atividadeCPBS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atividadeCPBS);
        expect(expectedResult).toContain(atividadeCPBS2);
      });

      it('should accept null and undefined values', () => {
        const atividadeCPBS: IAtividadeCPBS = sampleWithRequiredData;
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing([], null, atividadeCPBS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atividadeCPBS);
      });

      it('should return initial array if no AtividadeCPBS is added', () => {
        const atividadeCPBSCollection: IAtividadeCPBS[] = [sampleWithRequiredData];
        expectedResult = service.addAtividadeCPBSToCollectionIfMissing(atividadeCPBSCollection, undefined, null);
        expect(expectedResult).toEqual(atividadeCPBSCollection);
      });
    });

    describe('compareAtividadeCPBS', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAtividadeCPBS(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAtividadeCPBS(entity1, entity2);
        const compareResult2 = service.compareAtividadeCPBS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAtividadeCPBS(entity1, entity2);
        const compareResult2 = service.compareAtividadeCPBS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAtividadeCPBS(entity1, entity2);
        const compareResult2 = service.compareAtividadeCPBS(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
