import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INfse, NewNfse } from '../nfse.model';

export type PartialUpdateNfse = Partial<INfse> & Pick<INfse, 'id'>;

export type EntityResponseType = HttpResponse<INfse>;
export type EntityArrayResponseType = HttpResponse<INfse[]>;

@Injectable({ providedIn: 'root' })
export class NfseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nfses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nfse: NewNfse): Observable<EntityResponseType> {
    return this.http.post<INfse>(this.resourceUrl, nfse, { observe: 'response' });
  }

  update(nfse: INfse): Observable<EntityResponseType> {
    return this.http.put<INfse>(`${this.resourceUrl}/${this.getNfseIdentifier(nfse)}`, nfse, { observe: 'response' });
  }

  partialUpdate(nfse: PartialUpdateNfse): Observable<EntityResponseType> {
    return this.http.patch<INfse>(`${this.resourceUrl}/${this.getNfseIdentifier(nfse)}`, nfse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INfse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INfse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNfseIdentifier(nfse: Pick<INfse, 'id'>): number {
    return nfse.id;
  }

  compareNfse(o1: Pick<INfse, 'id'> | null, o2: Pick<INfse, 'id'> | null): boolean {
    return o1 && o2 ? this.getNfseIdentifier(o1) === this.getNfseIdentifier(o2) : o1 === o2;
  }

  addNfseToCollectionIfMissing<Type extends Pick<INfse, 'id'>>(
    nfseCollection: Type[],
    ...nfsesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nfses: Type[] = nfsesToCheck.filter(isPresent);
    if (nfses.length > 0) {
      const nfseCollectionIdentifiers = nfseCollection.map(nfseItem => this.getNfseIdentifier(nfseItem)!);
      const nfsesToAdd = nfses.filter(nfseItem => {
        const nfseIdentifier = this.getNfseIdentifier(nfseItem);
        if (nfseCollectionIdentifiers.includes(nfseIdentifier)) {
          return false;
        }
        nfseCollectionIdentifiers.push(nfseIdentifier);
        return true;
      });
      return [...nfsesToAdd, ...nfseCollection];
    }
    return nfseCollection;
  }
}
