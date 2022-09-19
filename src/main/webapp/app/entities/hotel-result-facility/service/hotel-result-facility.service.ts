import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelResultFacility, NewHotelResultFacility } from '../hotel-result-facility.model';

export type PartialUpdateHotelResultFacility = Partial<IHotelResultFacility> & Pick<IHotelResultFacility, 'id'>;

type RestOf<T extends IHotelResultFacility | NewHotelResultFacility> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestHotelResultFacility = RestOf<IHotelResultFacility>;

export type NewRestHotelResultFacility = RestOf<NewHotelResultFacility>;

export type PartialUpdateRestHotelResultFacility = RestOf<PartialUpdateHotelResultFacility>;

export type EntityResponseType = HttpResponse<IHotelResultFacility>;
export type EntityArrayResponseType = HttpResponse<IHotelResultFacility[]>;

@Injectable({ providedIn: 'root' })
export class HotelResultFacilityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-result-facilities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hotelResultFacility: NewHotelResultFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResultFacility);
    return this.http
      .post<RestHotelResultFacility>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(hotelResultFacility: IHotelResultFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResultFacility);
    return this.http
      .put<RestHotelResultFacility>(`${this.resourceUrl}/${this.getHotelResultFacilityIdentifier(hotelResultFacility)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(hotelResultFacility: PartialUpdateHotelResultFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelResultFacility);
    return this.http
      .patch<RestHotelResultFacility>(`${this.resourceUrl}/${this.getHotelResultFacilityIdentifier(hotelResultFacility)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHotelResultFacility>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHotelResultFacility[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHotelResultFacilityIdentifier(hotelResultFacility: Pick<IHotelResultFacility, 'id'>): number {
    return hotelResultFacility.id;
  }

  compareHotelResultFacility(o1: Pick<IHotelResultFacility, 'id'> | null, o2: Pick<IHotelResultFacility, 'id'> | null): boolean {
    return o1 && o2 ? this.getHotelResultFacilityIdentifier(o1) === this.getHotelResultFacilityIdentifier(o2) : o1 === o2;
  }

  addHotelResultFacilityToCollectionIfMissing<Type extends Pick<IHotelResultFacility, 'id'>>(
    hotelResultFacilityCollection: Type[],
    ...hotelResultFacilitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hotelResultFacilities: Type[] = hotelResultFacilitiesToCheck.filter(isPresent);
    if (hotelResultFacilities.length > 0) {
      const hotelResultFacilityCollectionIdentifiers = hotelResultFacilityCollection.map(
        hotelResultFacilityItem => this.getHotelResultFacilityIdentifier(hotelResultFacilityItem)!
      );
      const hotelResultFacilitiesToAdd = hotelResultFacilities.filter(hotelResultFacilityItem => {
        const hotelResultFacilityIdentifier = this.getHotelResultFacilityIdentifier(hotelResultFacilityItem);
        if (hotelResultFacilityCollectionIdentifiers.includes(hotelResultFacilityIdentifier)) {
          return false;
        }
        hotelResultFacilityCollectionIdentifiers.push(hotelResultFacilityIdentifier);
        return true;
      });
      return [...hotelResultFacilitiesToAdd, ...hotelResultFacilityCollection];
    }
    return hotelResultFacilityCollection;
  }

  protected convertDateFromClient<T extends IHotelResultFacility | NewHotelResultFacility | PartialUpdateHotelResultFacility>(
    hotelResultFacility: T
  ): RestOf<T> {
    return {
      ...hotelResultFacility,
      createdAt: hotelResultFacility.createdAt?.toJSON() ?? null,
      updatedAt: hotelResultFacility.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restHotelResultFacility: RestHotelResultFacility): IHotelResultFacility {
    return {
      ...restHotelResultFacility,
      createdAt: restHotelResultFacility.createdAt ? dayjs(restHotelResultFacility.createdAt) : undefined,
      updatedAt: restHotelResultFacility.updatedAt ? dayjs(restHotelResultFacility.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestHotelResultFacility>): HttpResponse<IHotelResultFacility> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestHotelResultFacility[]>): HttpResponse<IHotelResultFacility[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
