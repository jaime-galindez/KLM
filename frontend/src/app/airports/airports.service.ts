import {HttpClient, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/map'
import {Airport} from '../shared/model';

@Injectable()
export class AirportsService {

  constructor(private http: HttpClient) {

  }

  searchAirports(query: string): Observable<Airport[]> {
    return this.http.get<Airport[]>(`api/airport/find/${query}`);
  }
}
