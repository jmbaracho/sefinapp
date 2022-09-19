import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaskStatus, NewTaskStatus } from '../task-status.model';

export type PartialUpdateTaskStatus = Partial<ITaskStatus> & Pick<ITaskStatus, 'id'>;

type RestOf<T extends ITaskStatus | NewTaskStatus> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestTaskStatus = RestOf<ITaskStatus>;

export type NewRestTaskStatus = RestOf<NewTaskStatus>;

export type PartialUpdateRestTaskStatus = RestOf<PartialUpdateTaskStatus>;

export type EntityResponseType = HttpResponse<ITaskStatus>;
export type EntityArrayResponseType = HttpResponse<ITaskStatus[]>;

@Injectable({ providedIn: 'root' })
export class TaskStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/task-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(taskStatus: NewTaskStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskStatus);
    return this.http
      .post<RestTaskStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(taskStatus: ITaskStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskStatus);
    return this.http
      .put<RestTaskStatus>(`${this.resourceUrl}/${this.getTaskStatusIdentifier(taskStatus)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(taskStatus: PartialUpdateTaskStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskStatus);
    return this.http
      .patch<RestTaskStatus>(`${this.resourceUrl}/${this.getTaskStatusIdentifier(taskStatus)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTaskStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTaskStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTaskStatusIdentifier(taskStatus: Pick<ITaskStatus, 'id'>): number {
    return taskStatus.id;
  }

  compareTaskStatus(o1: Pick<ITaskStatus, 'id'> | null, o2: Pick<ITaskStatus, 'id'> | null): boolean {
    return o1 && o2 ? this.getTaskStatusIdentifier(o1) === this.getTaskStatusIdentifier(o2) : o1 === o2;
  }

  addTaskStatusToCollectionIfMissing<Type extends Pick<ITaskStatus, 'id'>>(
    taskStatusCollection: Type[],
    ...taskStatusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const taskStatuses: Type[] = taskStatusesToCheck.filter(isPresent);
    if (taskStatuses.length > 0) {
      const taskStatusCollectionIdentifiers = taskStatusCollection.map(taskStatusItem => this.getTaskStatusIdentifier(taskStatusItem)!);
      const taskStatusesToAdd = taskStatuses.filter(taskStatusItem => {
        const taskStatusIdentifier = this.getTaskStatusIdentifier(taskStatusItem);
        if (taskStatusCollectionIdentifiers.includes(taskStatusIdentifier)) {
          return false;
        }
        taskStatusCollectionIdentifiers.push(taskStatusIdentifier);
        return true;
      });
      return [...taskStatusesToAdd, ...taskStatusCollection];
    }
    return taskStatusCollection;
  }

  protected convertDateFromClient<T extends ITaskStatus | NewTaskStatus | PartialUpdateTaskStatus>(taskStatus: T): RestOf<T> {
    return {
      ...taskStatus,
      createdAt: taskStatus.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: taskStatus.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTaskStatus: RestTaskStatus): ITaskStatus {
    return {
      ...restTaskStatus,
      createdAt: restTaskStatus.createdAt ? dayjs(restTaskStatus.createdAt) : undefined,
      updatedAt: restTaskStatus.updatedAt ? dayjs(restTaskStatus.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTaskStatus>): HttpResponse<ITaskStatus> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTaskStatus[]>): HttpResponse<ITaskStatus[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
