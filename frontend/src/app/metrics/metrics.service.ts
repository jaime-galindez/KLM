import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Metrics} from '../shared/model/metrics.model';
import {Observable} from 'rxjs';

@Injectable()
export class MetricsService {

  constructor(private http: HttpClient) {}


  getMetrics(): Observable<Metrics> {
    return this.http.get<Metrics>("api/metrics");
  }
}
