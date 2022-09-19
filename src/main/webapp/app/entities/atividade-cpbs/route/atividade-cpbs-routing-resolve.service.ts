import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAtividadeCPBS } from '../atividade-cpbs.model';
import { AtividadeCPBSService } from '../service/atividade-cpbs.service';

@Injectable({ providedIn: 'root' })
export class AtividadeCPBSRoutingResolveService implements Resolve<IAtividadeCPBS | null> {
  constructor(protected service: AtividadeCPBSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAtividadeCPBS | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((atividadeCPBS: HttpResponse<IAtividadeCPBS>) => {
          if (atividadeCPBS.body) {
            return of(atividadeCPBS.body);
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
