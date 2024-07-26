import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAlloyHttp } from '../alloy-http.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../alloy-http.test-samples';

import { AlloyHttpService, RestAlloyHttp } from './alloy-http.service';

const requireRestSample: RestAlloyHttp = {
  ...sampleWithRequiredData,
  createTime: sampleWithRequiredData.createTime?.toJSON(),
  updateTime: sampleWithRequiredData.updateTime?.toJSON(),
};

describe('AlloyHttp Service', () => {
  let service: AlloyHttpService;
  let httpMock: HttpTestingController;
  let expectedResult: IAlloyHttp | IAlloyHttp[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AlloyHttpService);
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

    it('should create a AlloyHttp', () => {
      const alloyHttp = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(alloyHttp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AlloyHttp', () => {
      const alloyHttp = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(alloyHttp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AlloyHttp', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AlloyHttp', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AlloyHttp', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAlloyHttpToCollectionIfMissing', () => {
      it('should add a AlloyHttp to an empty array', () => {
        const alloyHttp: IAlloyHttp = sampleWithRequiredData;
        expectedResult = service.addAlloyHttpToCollectionIfMissing([], alloyHttp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alloyHttp);
      });

      it('should not add a AlloyHttp to an array that contains it', () => {
        const alloyHttp: IAlloyHttp = sampleWithRequiredData;
        const alloyHttpCollection: IAlloyHttp[] = [
          {
            ...alloyHttp,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAlloyHttpToCollectionIfMissing(alloyHttpCollection, alloyHttp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AlloyHttp to an array that doesn't contain it", () => {
        const alloyHttp: IAlloyHttp = sampleWithRequiredData;
        const alloyHttpCollection: IAlloyHttp[] = [sampleWithPartialData];
        expectedResult = service.addAlloyHttpToCollectionIfMissing(alloyHttpCollection, alloyHttp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alloyHttp);
      });

      it('should add only unique AlloyHttp to an array', () => {
        const alloyHttpArray: IAlloyHttp[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const alloyHttpCollection: IAlloyHttp[] = [sampleWithRequiredData];
        expectedResult = service.addAlloyHttpToCollectionIfMissing(alloyHttpCollection, ...alloyHttpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const alloyHttp: IAlloyHttp = sampleWithRequiredData;
        const alloyHttp2: IAlloyHttp = sampleWithPartialData;
        expectedResult = service.addAlloyHttpToCollectionIfMissing([], alloyHttp, alloyHttp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alloyHttp);
        expect(expectedResult).toContain(alloyHttp2);
      });

      it('should accept null and undefined values', () => {
        const alloyHttp: IAlloyHttp = sampleWithRequiredData;
        expectedResult = service.addAlloyHttpToCollectionIfMissing([], null, alloyHttp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alloyHttp);
      });

      it('should return initial array if no AlloyHttp is added', () => {
        const alloyHttpCollection: IAlloyHttp[] = [sampleWithRequiredData];
        expectedResult = service.addAlloyHttpToCollectionIfMissing(alloyHttpCollection, undefined, null);
        expect(expectedResult).toEqual(alloyHttpCollection);
      });
    });

    describe('compareAlloyHttp', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAlloyHttp(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAlloyHttp(entity1, entity2);
        const compareResult2 = service.compareAlloyHttp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAlloyHttp(entity1, entity2);
        const compareResult2 = service.compareAlloyHttp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAlloyHttp(entity1, entity2);
        const compareResult2 = service.compareAlloyHttp(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
