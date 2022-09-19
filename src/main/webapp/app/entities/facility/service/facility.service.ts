import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFacility, NewFacility } from '../facility.model';

export type PartialUpdateFacility = Partial<IFacility> & Pick<IFacility, 'id'>;

type RestOf<T extends IFacility | NewFacility> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestFacility = RestOf<IFacility>;

export type NewRestFacility = RestOf<NewFacility>;

export type PartialUpdateRestFacility = RestOf<PartialUpdateFacility>;

export type EntityResponseType = HttpResponse<IFacility>;
export type EntityArrayResponseType = HttpResponse<IFacility[]>;

@Injectable({ providedIn: 'root' })
export class FacilityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/facilities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(facility: NewFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facility);
    return this.http
      .post<RestFacility>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(facility: IFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facility);
    return this.http
      .put<RestFacility>(`${this.resourceUrl}/${this.getFacilityIdentifier(facility)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(facility: PartialUpdateFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facility);
    return this.http
      .patch<RestFacility>(`${this.resourceUrl}/${this.getFacilityIdentifier(facility)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFacility>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFacility[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFacilityIdentifier(facility: Pick<IFacility, 'id'>): number {
    return facility.id;
  }

  compareFacility(o1: Pick<IFacility, 'id'> | null, o2: Pick<IFacility, 'id'> | null): boolean {
    return o1 && o2 ? this.getFacilityIdentifier(o1) === this.getFacilityIdentifier(o2) : o1 === o2;
  }

  addFacilityToCollectionIfMissing<Type extends Pick<IFacility, 'id'>>(
    facilityCollection: Type[],
    ...facilitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const facilities: Type[] = facilitiesToCheck.filter(isPresent);
    if (facilities.length > 0) {
      const facilityCollectionIdentifiers = facilityCollection.map(facilityItem => this.getFacilityIdentifier(facilityItem)!);
      const facilitiesToAdd = facilities.filter(facilityItem => {
        const facilityIdentifier = this.getFacilityIdentifier(facilityItem);
        if (facilityCollectionIdentifiers.includes(facilityIdentifier)) {
          return false;
        }
        facilityCollectionIdentifiers.push(facilityIdentifier);
        return true;
      });
      return [...facilitiesToAdd, ...facilityCollection];
    }
    return facilityCollection;
  }

  protected convertDateFromClient<T extends IFacility | NewFacility | PartialUpdateFacility>(facility: T): RestOf<T> {
    return {
      ...facility,
      createdAt: facility.createdAt?.toJSON() ?? null,
      updatedAt: facility.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restFacility: RestFacility): IFacility {
    return {
      ...restFacility,
      createdAt: restFacility.createdAt ? dayjs(restFacility.createdAt) : undefined,
      updatedAt: restFacility.updatedAt ? dayjs(restFacility.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFacility>): HttpResponse<IFacility> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFacility[]>): HttpResponse<IFacility[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
