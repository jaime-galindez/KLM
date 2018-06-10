import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Fare} from '../shared/model/fare.model';

@Injectable()
export class FaresService {

  constructor(private http: HttpClient) {}

  getFares(originAirportCode: string, destinationAirportCode: string): Observable<Fare> {
    return this.http.get<Fare>(`api/fares/${originAirportCode}/${destinationAirportCode}`)
  }
}
