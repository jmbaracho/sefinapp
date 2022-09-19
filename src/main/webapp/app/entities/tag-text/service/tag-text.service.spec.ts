import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITagText } from '../tag-text.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tag-text.test-samples';

import { TagTextService } from './tag-text.service';

const requireRestSample: ITagText = {
  ...sampleWithRequiredData,
};

describe('TagText Service', () => {
  let service: TagTextService;
  let httpMock: HttpTestingController;
  let expectedResult: ITagText | ITagText[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TagTextService);
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

    it('should create a TagText', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tagText = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tagText).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TagText', () => {
      const tagText = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tagText).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TagText', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TagText', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TagText', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTagTextToCollectionIfMissing', () => {
      it('should add a TagText to an empty array', () => {
        const tagText: ITagText = sampleWithRequiredData;
        expectedResult = service.addTagTextToCollectionIfMissing([], tagText);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tagText);
      });

      it('should not add a TagText to an array that contains it', () => {
        const tagText: ITagText = sampleWithRequiredData;
        const tagTextCollection: ITagText[] = [
          {
            ...tagText,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTagTextToCollectionIfMissing(tagTextCollection, tagText);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TagText to an array that doesn't contain it", () => {
        const tagText: ITagText = sampleWithRequiredData;
        const tagTextCollection: ITagText[] = [sampleWithPartialData];
        expectedResult = service.addTagTextToCollectionIfMissing(tagTextCollection, tagText);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tagText);
      });

      it('should add only unique TagText to an array', () => {
        const tagTextArray: ITagText[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tagTextCollection: ITagText[] = [sampleWithRequiredData];
        expectedResult = service.addTagTextToCollectionIfMissing(tagTextCollection, ...tagTextArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tagText: ITagText = sampleWithRequiredData;
        const tagText2: ITagText = sampleWithPartialData;
        expectedResult = service.addTagTextToCollectionIfMissing([], tagText, tagText2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tagText);
        expect(expectedResult).toContain(tagText2);
      });

      it('should accept null and undefined values', () => {
        const tagText: ITagText = sampleWithRequiredData;
        expectedResult = service.addTagTextToCollectionIfMissing([], null, tagText, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tagText);
      });

      it('should return initial array if no TagText is added', () => {
        const tagTextCollection: ITagText[] = [sampleWithRequiredData];
        expectedResult = service.addTagTextToCollectionIfMissing(tagTextCollection, undefined, null);
        expect(expectedResult).toEqual(tagTextCollection);
      });
    });

    describe('compareTagText', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTagText(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTagText(entity1, entity2);
        const compareResult2 = service.compareTagText(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTagText(entity1, entity2);
        const compareResult2 = service.compareTagText(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTagText(entity1, entity2);
        const compareResult2 = service.compareTagText(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
