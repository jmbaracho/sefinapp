import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInscricaoCPBS } from '../inscricao-cpbs.model';
import { InscricaoCPBSService } from '../service/inscricao-cpbs.service';

@Injectable({ providedIn: 'root' })
export class InscricaoCPBSRoutingResolveService implements Resolve<IInscricaoCPBS | null> {
  constructor(protected service: InscricaoCPBSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInscricaoCPBS | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inscricaoCPBS: HttpResponse<IInscricaoCPBS>) => {
          if (inscricaoCPBS.body) {
            return of(inscricaoCPBS.body);
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
