import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITagText, NewTagText } from '../tag-text.model';

export type PartialUpdateTagText = Partial<ITagText> & Pick<ITagText, 'id'>;

export type EntityResponseType = HttpResponse<ITagText>;
export type EntityArrayResponseType = HttpResponse<ITagText[]>;

@Injectable({ providedIn: 'root' })
export class TagTextService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tag-texts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tagText: NewTagText): Observable<EntityResponseType> {
    return this.http.post<ITagText>(this.resourceUrl, tagText, { observe: 'response' });
  }

  update(tagText: ITagText): Observable<EntityResponseType> {
    return this.http.put<ITagText>(`${this.resourceUrl}/${this.getTagTextIdentifier(tagText)}`, tagText, { observe: 'response' });
  }

  partialUpdate(tagText: PartialUpdateTagText): Observable<EntityResponseType> {
    return this.http.patch<ITagText>(`${this.resourceUrl}/${this.getTagTextIdentifier(tagText)}`, tagText, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITagText>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagText[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTagTextIdentifier(tagText: Pick<ITagText, 'id'>): number {
    return tagText.id;
  }

  compareTagText(o1: Pick<ITagText, 'id'> | null, o2: Pick<ITagText, 'id'> | null): boolean {
    return o1 && o2 ? this.getTagTextIdentifier(o1) === this.getTagTextIdentifier(o2) : o1 === o2;
  }

  addTagTextToCollectionIfMissing<Type extends Pick<ITagText, 'id'>>(
    tagTextCollection: Type[],
    ...tagTextsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tagTexts: Type[] = tagTextsToCheck.filter(isPresent);
    if (tagTexts.length > 0) {
      const tagTextCollectionIdentifiers = tagTextCollection.map(tagTextItem => this.getTagTextIdentifier(tagTextItem)!);
      const tagTextsToAdd = tagTexts.filter(tagTextItem => {
        const tagTextIdentifier = this.getTagTextIdentifier(tagTextItem);
        if (tagTextCollectionIdentifiers.includes(tagTextIdentifier)) {
          return false;
        }
        tagTextCollectionIdentifiers.push(tagTextIdentifier);
        return true;
      });
      return [...tagTextsToAdd, ...tagTextCollection];
    }
    return tagTextCollection;
  }
}
