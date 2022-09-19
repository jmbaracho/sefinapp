import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelResult } from '../hotel-result.model';
import { HotelResultService } from '../service/hotel-result.service';

@Injectable({ providedIn: 'root' })
export class HotelResultRoutingResolveService implements Resolve<IHotelResult | null> {
  constructor(protected service: HotelResultService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelResult | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotelResult: HttpResponse<IHotelResult>) => {
          if (hotelResult.body) {
            return of(hotelResult.body);
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
