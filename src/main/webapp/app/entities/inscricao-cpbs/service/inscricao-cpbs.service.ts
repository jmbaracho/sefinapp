import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInscricaoCPBS, NewInscricaoCPBS } from '../inscricao-cpbs.model';

export type PartialUpdateInscricaoCPBS = Partial<IInscricaoCPBS> & Pick<IInscricaoCPBS, 'id'>;

export type EntityResponseType = HttpResponse<IInscricaoCPBS>;
export type EntityArrayResponseType = HttpResponse<IInscricaoCPBS[]>;

@Injectable({ providedIn: 'root' })
export class InscricaoCPBSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inscricao-cpbs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inscricaoCPBS: NewInscricaoCPBS): Observable<EntityResponseType> {
    return this.http.post<IInscricaoCPBS>(this.resourceUrl, inscricaoCPBS, { observe: 'response' });
  }

  update(inscricaoCPBS: IInscricaoCPBS): Observable<EntityResponseType> {
    return this.http.put<IInscricaoCPBS>(`${this.resourceUrl}/${this.getInscricaoCPBSIdentifier(inscricaoCPBS)}`, inscricaoCPBS, {
      observe: 'response',
    });
  }

  partialUpdate(inscricaoCPBS: PartialUpdateInscricaoCPBS): Observable<EntityResponseType> {
    return this.http.patch<IInscricaoCPBS>(`${this.resourceUrl}/${this.getInscricaoCPBSIdentifier(inscricaoCPBS)}`, inscricaoCPBS, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInscricaoCPBS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInscricaoCPBS[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInscricaoCPBSIdentifier(inscricaoCPBS: Pick<IInscricaoCPBS, 'id'>): number {
    return inscricaoCPBS.id;
  }

  compareInscricaoCPBS(o1: Pick<IInscricaoCPBS, 'id'> | null, o2: Pick<IInscricaoCPBS, 'id'> | null): boolean {
    return o1 && o2 ? this.getInscricaoCPBSIdentifier(o1) === this.getInscricaoCPBSIdentifier(o2) : o1 === o2;
  }

  addInscricaoCPBSToCollectionIfMissing<Type extends Pick<IInscricaoCPBS, 'id'>>(
    inscricaoCPBSCollection: Type[],
    ...inscricaoCPBSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const inscricaoCPBS: Type[] = inscricaoCPBSToCheck.filter(isPresent);
    if (inscricaoCPBS.length > 0) {
      const inscricaoCPBSCollectionIdentifiers = inscricaoCPBSCollection.map(
        inscricaoCPBSItem => this.getInscricaoCPBSIdentifier(inscricaoCPBSItem)!
      );
      const inscricaoCPBSToAdd = inscricaoCPBS.filter(inscricaoCPBSItem => {
        const inscricaoCPBSIdentifier = this.getInscricaoCPBSIdentifier(inscricaoCPBSItem);
        if (inscricaoCPBSCollectionIdentifiers.includes(inscricaoCPBSIdentifier)) {
          return false;
        }
        inscricaoCPBSCollectionIdentifiers.push(inscricaoCPBSIdentifier);
        return true;
      });
      return [...inscricaoCPBSToAdd, ...inscricaoCPBSCollection];
    }
    return inscricaoCPBSCollection;
  }
}
