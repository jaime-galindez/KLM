import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AirportsService} from '../airports/airports.service';
import {Subscription} from 'rxjs/Subscription';
import {Airport} from '../shared/model';
import {Fare} from '../shared/model/fare.model';
import {FaresService} from './fares.service';

@Component({
  selector: 'app-fares',
  templateUrl: './fares.component.html',
  styleUrls: ['./fares.component.css']
})
export class FaresComponent implements OnInit {

  public faresForm: FormGroup;
  public originAirportsResults: Airport[];
  public destinationAirportsResults: Airport[];
  public fare: Fare;

  private originAirportsResults$: Subscription;
  private destinationAirportsResults$: Subscription;
  private fare$: Subscription;

  constructor(
    private fb: FormBuilder,
    private airportService: AirportsService,
    private fareService: FaresService
  ) {
  }

  ngOnInit() {
    this.initFaresForm();
  }

  initFaresForm() {
    this.faresForm = this.fb.group({
      'originAirport': this.fb.control(''),
      'destinationAirport': this.fb.control('')
    })
  }

  searchOriginAirports(event) {
    console.log(event);
    this.closeSubscription(this.originAirportsResults$);
    this.originAirportsResults$ = this.airportService.searchAirports(event.query).subscribe((airports: Airport[]) => {
        this.originAirportsResults = airports;
      });
  }

  searchDestinationAirports(event) {
    this.closeSubscription(this.destinationAirportsResults$);
    this.destinationAirportsResults$ = this.airportService.searchAirports(event.query).subscribe((airports: Airport[]) => {
        this.destinationAirportsResults = airports;
      });
  }

  calculateFare() {
    this.closeSubscription(this.fare$);
    this.fare$ = this.fareService.getFares(this.faresForm.value.originAirport.code, this.faresForm.value.destinationAirport.code).subscribe((f: Fare) => this.fare = f);
  }

  closeSubscription(subscription: Subscription) {
    if (subscription) {
      subscription.unsubscribe();
    }
  }
}
