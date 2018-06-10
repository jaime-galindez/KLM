import {FaresComponent} from './fares/fares.component';
import {PageNotFoundComponent} from './shared/page-not-found/page-not-found.component';
import {Routes, RouterModule} from '@angular/router';
import {MetricsComponent} from './metrics/metrics.component';

const appRoutes: Routes = [
  { path: 'fares', component: FaresComponent },
  { path: 'metrics',      component: MetricsComponent },
  { path: '',
    redirectTo: '/fares',
    pathMatch: 'full'
  },
  { path: '**', component: PageNotFoundComponent }
];

export const APP_ROUTES = RouterModule.forRoot(appRoutes, {useHash: true});
