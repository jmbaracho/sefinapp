import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITagText } from '../tag-text.model';
import { TagTextService } from '../service/tag-text.service';

@Injectable({ providedIn: 'root' })
export class TagTextRoutingResolveService implements Resolve<ITagText | null> {
  constructor(protected service: TagTextService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITagText | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tagText: HttpResponse<ITagText>) => {
          if (tagText.body) {
            return of(tagText.body);
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
