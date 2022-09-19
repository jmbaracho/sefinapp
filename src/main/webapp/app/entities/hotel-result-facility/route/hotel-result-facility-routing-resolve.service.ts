import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelResultFacility } from '../hotel-result-facility.model';
import { HotelResultFacilityService } from '../service/hotel-result-facility.service';

@Injectable({ providedIn: 'root' })
export class HotelResultFacilityRoutingResolveService implements Resolve<IHotelResultFacility | null> {
  constructor(protected service: HotelResultFacilityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelResultFacility | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotelResultFacility: HttpResponse<IHotelResultFacility>) => {
          if (hotelResultFacility.body) {
            return of(hotelResultFacility.body);
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
