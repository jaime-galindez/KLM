import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {MenubarModule} from 'primeng/menubar';
import {AutoCompleteModule} from 'primeng/autocomplete';

import {AppComponent} from './app.component';
import {FaresComponent} from './fares/fares.component';
import {PageNotFoundComponent} from './shared/page-not-found/page-not-found.component';
import {APP_ROUTES} from './app.routes';
import {AirportsService} from './airports/airports.service';
import {FaresService} from './fares/fares.service';
import { MetricsComponent } from './metrics/metrics.component';
import {MetricsService} from './metrics/metrics.service';

@NgModule({
  declarations: [
    AppComponent,
    FaresComponent,
    PageNotFoundComponent,
    MetricsComponent
  ],
  imports: [
    BrowserModule,
    MenubarModule,
    AutoCompleteModule,
    ReactiveFormsModule,
    HttpClientModule,
    APP_ROUTES
  ],
  providers: [
    AirportsService,
    FaresService,
    MetricsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
