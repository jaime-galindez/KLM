import {Airport} from './airport.model';


export class Fare {
  amount: number;
  currency: string;
  originAirport: Airport;
  destinationAirport: Airport;
}
