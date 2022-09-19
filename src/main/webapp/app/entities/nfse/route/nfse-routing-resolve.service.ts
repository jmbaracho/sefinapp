import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INfse } from '../nfse.model';
import { NfseService } from '../service/nfse.service';

@Injectable({ providedIn: 'root' })
export class NfseRoutingResolveService implements Resolve<INfse | null> {
  constructor(protected service: NfseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INfse | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nfse: HttpResponse<INfse>) => {
          if (nfse.body) {
            return of(nfse.body);
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
