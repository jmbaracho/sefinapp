import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IScheduleExecution, NewScheduleExecution } from '../schedule-execution.model';

export type PartialUpdateScheduleExecution = Partial<IScheduleExecution> & Pick<IScheduleExecution, 'id'>;

type RestOf<T extends IScheduleExecution | NewScheduleExecution> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestScheduleExecution = RestOf<IScheduleExecution>;

export type NewRestScheduleExecution = RestOf<NewScheduleExecution>;

export type PartialUpdateRestScheduleExecution = RestOf<PartialUpdateScheduleExecution>;

export type EntityResponseType = HttpResponse<IScheduleExecution>;
export type EntityArrayResponseType = HttpResponse<IScheduleExecution[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleExecutionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schedule-executions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(scheduleExecution: NewScheduleExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scheduleExecution);
    return this.http
      .post<RestScheduleExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(scheduleExecution: IScheduleExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scheduleExecution);
    return this.http
      .put<RestScheduleExecution>(`${this.resourceUrl}/${this.getScheduleExecutionIdentifier(scheduleExecution)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(scheduleExecution: PartialUpdateScheduleExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scheduleExecution);
    return this.http
      .patch<RestScheduleExecution>(`${this.resourceUrl}/${this.getScheduleExecutionIdentifier(scheduleExecution)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestScheduleExecution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestScheduleExecution[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getScheduleExecutionIdentifier(scheduleExecution: Pick<IScheduleExecution, 'id'>): number {
    return scheduleExecution.id;
  }

  compareScheduleExecution(o1: Pick<IScheduleExecution, 'id'> | null, o2: Pick<IScheduleExecution, 'id'> | null): boolean {
    return o1 && o2 ? this.getScheduleExecutionIdentifier(o1) === this.getScheduleExecutionIdentifier(o2) : o1 === o2;
  }

  addScheduleExecutionToCollectionIfMissing<Type extends Pick<IScheduleExecution, 'id'>>(
    scheduleExecutionCollection: Type[],
    ...scheduleExecutionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const scheduleExecutions: Type[] = scheduleExecutionsToCheck.filter(isPresent);
    if (scheduleExecutions.length > 0) {
      const scheduleExecutionCollectionIdentifiers = scheduleExecutionCollection.map(
        scheduleExecutionItem => this.getScheduleExecutionIdentifier(scheduleExecutionItem)!
      );
      const scheduleExecutionsToAdd = scheduleExecutions.filter(scheduleExecutionItem => {
        const scheduleExecutionIdentifier = this.getScheduleExecutionIdentifier(scheduleExecutionItem);
        if (scheduleExecutionCollectionIdentifiers.includes(scheduleExecutionIdentifier)) {
          return false;
        }
        scheduleExecutionCollectionIdentifiers.push(scheduleExecutionIdentifier);
        return true;
      });
      return [...scheduleExecutionsToAdd, ...scheduleExecutionCollection];
    }
    return scheduleExecutionCollection;
  }

  protected convertDateFromClient<T extends IScheduleExecution | NewScheduleExecution | PartialUpdateScheduleExecution>(
    scheduleExecution: T
  ): RestOf<T> {
    return {
      ...scheduleExecution,
      createdAt: scheduleExecution.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: scheduleExecution.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restScheduleExecution: RestScheduleExecution): IScheduleExecution {
    return {
      ...restScheduleExecution,
      createdAt: restScheduleExecution.createdAt ? dayjs(restScheduleExecution.createdAt) : undefined,
      updatedAt: restScheduleExecution.updatedAt ? dayjs(restScheduleExecution.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestScheduleExecution>): HttpResponse<IScheduleExecution> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestScheduleExecution[]>): HttpResponse<IScheduleExecution[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
