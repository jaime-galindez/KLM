import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  public items: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'Fares',
        routerLink: '/fares'
      },
      {
        label: 'Metrics',
        routerLink: '/metrics'
      }];
  }
}
