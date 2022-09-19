import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IScheduleExecution } from '../schedule-execution.model';
import { ScheduleExecutionService } from '../service/schedule-execution.service';

@Injectable({ providedIn: 'root' })
export class ScheduleExecutionRoutingResolveService implements Resolve<IScheduleExecution | null> {
  constructor(protected service: ScheduleExecutionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheduleExecution | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((scheduleExecution: HttpResponse<IScheduleExecution>) => {
          if (scheduleExecution.body) {
            return of(scheduleExecution.body);
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
