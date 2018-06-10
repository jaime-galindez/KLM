import {Component, OnDestroy, OnInit} from '@angular/core';
import {MetricsService} from './metrics.service';
import {Metrics} from '../shared/model/metrics.model';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-metrics',
  templateUrl: './metrics.component.html',
  styleUrls: ['./metrics.component.css']
})
export class MetricsComponent implements OnInit, OnDestroy {

  public metrics: Metrics;
  public metrics$: Subscription;

  constructor(
    private metricsService: MetricsService
  ) { }

  ngOnInit() {
    this.metrics$ = this.metricsService.getMetrics().subscribe((m: Metrics) => this.metrics = m);
  }

  ngOnDestroy() {
    if (this.metrics$) {
      this.metrics$.unsubscribe();
    }
  }
}
