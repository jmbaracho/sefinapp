import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelResult, NewHotelResult } from '../hotel-result.model';

export type PartialUpdateHotelResult = Partial<IHotelResult> & Pick<IHotelResult, 'id'>;

type RestOf<T extends IHotelResult | NewHotelResult> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestHotelResult = RestOf<IHotelResult>;

export type NewRestHotelResult = RestOf<NewHotelResult>;

export type PartialUpdateRestHotelResult = RestOf<PartialUpdateHotelResult>;

export type EntityResponseType = HttpResponse<IHotelResult>;
export type EntityArrayResponseType = HttpResponse<IHotelResult[]>;

@Injectable({ providedIn: 'root' })
export class HotelResultService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-results');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hotelResult: NewHotelResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResult);
    return this.http
      .post<RestHotelResult>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(hotelResult: IHotelResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResult);
    return this.http
      .put<RestHotelResult>(`${this.resourceUrl}/${this.getHotelResultIdentifier(hotelResult)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(hotelResult: PartialUpdateHotelResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResult);
    return this.http
      .patch<RestHotelResult>(`${this.resourceUrl}/${this.getHotelResultIdentifier(hotelResult)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHotelResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHotelResult[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHotelResultIdentifier(hotelResult: Pick<IHotelResult, 'id'>): number {
    return hotelResult.id;
  }

  compareHotelResult(o1: Pick<IHotelResult, 'id'> | null, o2: Pick<IHotelResult, 'id'> | null): boolean {
    return o1 && o2 ? this.getHotelResultIdentifier(o1) === this.getHotelResultIdentifier(o2) : o1 === o2;
  }

  addHotelResultToCollectionIfMissing<Type extends Pick<IHotelResult, 'id'>>(
    hotelResultCollection: Type[],
    ...hotelResultsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hotelResults: Type[] = hotelResultsToCheck.filter(isPresent);
    if (hotelResults.length > 0) {
      const hotelResultCollectionIdentifiers = hotelResultCollection.map(
        hotelResultItem => this.getHotelResultIdentifier(hotelResultItem)!
      );
      const hotelResultsToAdd = hotelResults.filter(hotelResultItem => {
        const hotelResultIdentifier = this.getHotelResultIdentifier(hotelResultItem);
        if (hotelResultCollectionIdentifiers.includes(hotelResultIdentifier)) {
          return false;
        }
        hotelResultCollectionIdentifiers.push(hotelResultIdentifier);
        return true;
      });
      return [...hotelResultsToAdd, ...hotelResultCollection];
    }
    return hotelResultCollection;
  }

  protected convertDateFromClient<T extends IHotelResult | NewHotelResult | PartialUpdateHotelResult>(hotelResult: T): RestOf<T> {
    return {
      ...hotelResult,
      createdAt: hotelResult.createdAt?.toJSON() ?? null,
      updatedAt: hotelResult.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restHotelResult: RestHotelResult): IHotelResult {
    return {
      ...restHotelResult,
      createdAt: restHotelResult.createdAt ? dayjs(restHotelResult.createdAt) : undefined,
      updatedAt: restHotelResult.updatedAt ? dayjs(restHotelResult.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestHotelResult>): HttpResponse<IHotelResult> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestHotelResult[]>): HttpResponse<IHotelResult[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
