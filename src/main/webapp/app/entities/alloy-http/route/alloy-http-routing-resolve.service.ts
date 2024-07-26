import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAlloyHttp } from '../alloy-http.model';
import { AlloyHttpService } from '../service/alloy-http.service';

const alloyHttpResolve = (route: ActivatedRouteSnapshot): Observable<null | IAlloyHttp> => {
  const id = route.params['id'];
  if (id) {
    return inject(AlloyHttpService)
      .find(id)
      .pipe(
        mergeMap((alloyHttp: HttpResponse<IAlloyHttp>) => {
          if (alloyHttp.body) {
            return of(alloyHttp.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default alloyHttpResolve;
