import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRawData, NewRawData } from '../raw-data.model';

export type PartialUpdateRawData = Partial<IRawData> & Pick<IRawData, 'id'>;

type RestOf<T extends IRawData | NewRawData> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRawData = RestOf<IRawData>;

export type NewRestRawData = RestOf<NewRawData>;

export type PartialUpdateRestRawData = RestOf<PartialUpdateRawData>;

export type EntityResponseType = HttpResponse<IRawData>;
export type EntityArrayResponseType = HttpResponse<IRawData[]>;

@Injectable({ providedIn: 'root' })
export class RawDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/raw-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rawData: NewRawData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawData);
    return this.http
      .post<RestRawData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(rawData: IRawData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawData);
    return this.http
      .put<RestRawData>(`${this.resourceUrl}/${this.getRawDataIdentifier(rawData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(rawData: PartialUpdateRawData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawData);
    return this.http
      .patch<RestRawData>(`${this.resourceUrl}/${this.getRawDataIdentifier(rawData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRawData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRawData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRawDataIdentifier(rawData: Pick<IRawData, 'id'>): number {
    return rawData.id;
  }

  compareRawData(o1: Pick<IRawData, 'id'> | null, o2: Pick<IRawData, 'id'> | null): boolean {
    return o1 && o2 ? this.getRawDataIdentifier(o1) === this.getRawDataIdentifier(o2) : o1 === o2;
  }

  addRawDataToCollectionIfMissing<Type extends Pick<IRawData, 'id'>>(
    rawDataCollection: Type[],
    ...rawDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const rawData: Type[] = rawDataToCheck.filter(isPresent);
    if (rawData.length > 0) {
      const rawDataCollectionIdentifiers = rawDataCollection.map(rawDataItem => this.getRawDataIdentifier(rawDataItem)!);
      const rawDataToAdd = rawData.filter(rawDataItem => {
        const rawDataIdentifier = this.getRawDataIdentifier(rawDataItem);
        if (rawDataCollectionIdentifiers.includes(rawDataIdentifier)) {
          return false;
        }
        rawDataCollectionIdentifiers.push(rawDataIdentifier);
        return true;
      });
      return [...rawDataToAdd, ...rawDataCollection];
    }
    return rawDataCollection;
  }

  protected convertDateFromClient<T extends IRawData | NewRawData | PartialUpdateRawData>(rawData: T): RestOf<T> {
    return {
      ...rawData,
      createdAt: rawData.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: rawData.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRawData: RestRawData): IRawData {
    return {
      ...restRawData,
      createdAt: restRawData.createdAt ? dayjs(restRawData.createdAt) : undefined,
      updatedAt: restRawData.updatedAt ? dayjs(restRawData.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRawData>): HttpResponse<IRawData> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRawData[]>): HttpResponse<IRawData[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
