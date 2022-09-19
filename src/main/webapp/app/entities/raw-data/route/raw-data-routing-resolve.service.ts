import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRawData } from '../raw-data.model';
import { RawDataService } from '../service/raw-data.service';

@Injectable({ providedIn: 'root' })
export class RawDataRoutingResolveService implements Resolve<IRawData | null> {
  constructor(protected service: RawDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRawData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rawData: HttpResponse<IRawData>) => {
          if (rawData.body) {
            return of(rawData.body);
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
