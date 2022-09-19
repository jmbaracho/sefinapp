import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAtividadeCPBS, NewAtividadeCPBS } from '../atividade-cpbs.model';

export type PartialUpdateAtividadeCPBS = Partial<IAtividadeCPBS> & Pick<IAtividadeCPBS, 'id'>;

export type EntityResponseType = HttpResponse<IAtividadeCPBS>;
export type EntityArrayResponseType = HttpResponse<IAtividadeCPBS[]>;

@Injectable({ providedIn: 'root' })
export class AtividadeCPBSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/atividade-cpbs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(atividadeCPBS: NewAtividadeCPBS): Observable<EntityResponseType> {
    return this.http.post<IAtividadeCPBS>(this.resourceUrl, atividadeCPBS, { observe: 'response' });
  }

  update(atividadeCPBS: IAtividadeCPBS): Observable<EntityResponseType> {
    return this.http.put<IAtividadeCPBS>(`${this.resourceUrl}/${this.getAtividadeCPBSIdentifier(atividadeCPBS)}`, atividadeCPBS, {
      observe: 'response',
    });
  }

  partialUpdate(atividadeCPBS: PartialUpdateAtividadeCPBS): Observable<EntityResponseType> {
    return this.http.patch<IAtividadeCPBS>(`${this.resourceUrl}/${this.getAtividadeCPBSIdentifier(atividadeCPBS)}`, atividadeCPBS, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAtividadeCPBS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAtividadeCPBS[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAtividadeCPBSIdentifier(atividadeCPBS: Pick<IAtividadeCPBS, 'id'>): number {
    return atividadeCPBS.id;
  }

  compareAtividadeCPBS(o1: Pick<IAtividadeCPBS, 'id'> | null, o2: Pick<IAtividadeCPBS, 'id'> | null): boolean {
    return o1 && o2 ? this.getAtividadeCPBSIdentifier(o1) === this.getAtividadeCPBSIdentifier(o2) : o1 === o2;
  }

  addAtividadeCPBSToCollectionIfMissing<Type extends Pick<IAtividadeCPBS, 'id'>>(
    atividadeCPBSCollection: Type[],
    ...atividadeCPBSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const atividadeCPBS: Type[] = atividadeCPBSToCheck.filter(isPresent);
    if (atividadeCPBS.length > 0) {
      const atividadeCPBSCollectionIdentifiers = atividadeCPBSCollection.map(
        atividadeCPBSItem => this.getAtividadeCPBSIdentifier(atividadeCPBSItem)!
      );
      const atividadeCPBSToAdd = atividadeCPBS.filter(atividadeCPBSItem => {
        const atividadeCPBSIdentifier = this.getAtividadeCPBSIdentifier(atividadeCPBSItem);
        if (atividadeCPBSCollectionIdentifiers.includes(atividadeCPBSIdentifier)) {
          return false;
        }
        atividadeCPBSCollectionIdentifiers.push(atividadeCPBSIdentifier);
        return true;
      });
      return [...atividadeCPBSToAdd, ...atividadeCPBSCollection];
    }
    return atividadeCPBSCollection;
  }
}
