import {Component, OnInit} from '@angular/core';
import {TradingViewwidget} from '../../assets/js/script';
import {NavbarService} from '../_services';
import {CurrencyService} from '../_services/currency.service';
import {Currency} from '../_models';
import {TradeService} from '../_services/trade.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from 'src/app/_models/user';
import {AlertService} from 'src/app/_services/alert.service';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {
  trade: { "type": any; "amount": any; "price": any; };
  user: User;
  public currencies: Currency[];
  public currentCurrency: Currency;
  orderForm: FormGroup;
  loading = false;
  submitted_buy = false;
  submitted_sell = false;

  constructor
  (private nav: NavbarService,
   private formBuilder: FormBuilder,
   private currencyService: CurrencyService,
   private alertService: AlertService,
   private tradeService: TradeService) {
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.currencyService.getById(1).subscribe(Currency => {

        console.log(Currency);
        this.currentCurrency = Currency;
      }
    );
    this.loadAllCurrencies();
    this.nav.hide();
    TradingViewwidget();
    this.orderForm = this.formBuilder.group({
      Price: ['', Validators.required],
      Amount: ['', Validators.required]
    });

  }

  private loadAllCurrencies() {
    this.currencyService.getAll().subscribe(Currencies => {
      console.log(Currencies);

      this.currencies = Currencies;

    });

  }

  private setCurrency(id) {
    console.log(id);
    this.currencyService.getById(id).subscribe(Currency => {
      this.currentCurrency = Currency;
    });
  }

  get f() {
    return this.orderForm.controls;
  }

  onSubmit(type) {
    console.log(type);
    if (type === 'BUY') {
      console.log('in');
      this.submitted_buy = true;
    } else
      this.submitted_sell = true;
    this.loading = true;

    // stop here if form is invalid
    if (this.orderForm.invalid) {
      return;
    }
    this.trade = {
      "type": type,
      "amount": this.orderForm.controls.Amount.value,
      "price": this.orderForm.controls.Price.value
    }
    this.tradeService.create(this.user.id, this.currentCurrency.id, this.trade)
      .subscribe(
        data => {
          this.alertService.success('Order successful', true);

        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
    // this.loading = true;
    // this.authenticationService.login(this.f.username.value, this.f.password.value)
    //     .pipe(first())
    //     .subscribe(
    //         data => {
    //             this.router.navigate([this.returnUrl]);
    //         },
    //         error => {
    //             this.alertService.error(error);
    //             this.loading = false;
    //         });
  }
}
