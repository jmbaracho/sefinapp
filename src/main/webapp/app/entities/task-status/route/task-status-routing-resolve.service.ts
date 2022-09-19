import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaskStatus } from '../task-status.model';
import { TaskStatusService } from '../service/task-status.service';

@Injectable({ providedIn: 'root' })
export class TaskStatusRoutingResolveService implements Resolve<ITaskStatus | null> {
  constructor(protected service: TaskStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskStatus | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taskStatus: HttpResponse<ITaskStatus>) => {
          if (taskStatus.body) {
            return of(taskStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
