import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AlloyHttpComponent } from './list/alloy-http.component';
import { AlloyHttpDetailComponent } from './detail/alloy-http-detail.component';
import { AlloyHttpUpdateComponent } from './update/alloy-http-update.component';
import AlloyHttpResolve from './route/alloy-http-routing-resolve.service';

const alloyHttpRoute: Routes = [
  {
    path: '',
    component: AlloyHttpComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlloyHttpDetailComponent,
    resolve: {
      alloyHttp: AlloyHttpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlloyHttpUpdateComponent,
    resolve: {
      alloyHttp: AlloyHttpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlloyHttpUpdateComponent,
    resolve: {
      alloyHttp: AlloyHttpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default alloyHttpRoute;
