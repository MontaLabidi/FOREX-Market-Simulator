import {Component, OnInit} from '@angular/core';

import {User} from '../_models';
import {NavbarService} from 'src/app/_services/navbar.service';
import {CurrencyService} from '../_services/currency.service';
import {Currency} from 'src/app/_models/currency';

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: User;
  currencies: Currency[];
  Top5currencies: Currency[];

  constructor(public nav: NavbarService,
              private currencyService: CurrencyService
  ) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    if (this.currentUser)
      this.nav.hide();
    this.loadAllCurrencies();
  }

  private loadAllCurrencies() {
    this.currencyService.getAll().subscribe(Currencies => {
      console.log(Currencies);

      this.currencies = Currencies;

    });

  }

  // deleteUser(id: number) {
  //   console.log(id)
  //     this.userService.delete(id).pipe(first()).subscribe(() => {
  //         this.loadAllUsers()
  //     });
  // }
  //
  // private loadAllUsers() {
  //     this.userService.getAll().pipe(first()).subscribe(users => {
  //
  //     });
  // }
}
