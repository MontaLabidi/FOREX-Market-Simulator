import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {AlertComponent} from './_directives';
import {AuthGuard} from './_guards';
import {ErrorInterceptor, JwtInterceptor} from './_helpers';
import {AlertService, AuthenticationService, UserService} from './_services';
import {HomeComponent} from './home';
import {LoginComponent} from './login';
import {RegisterComponent} from './register';
import {NavbarComponent} from './navbar/navbar.component';
import {UserComponent} from './user/user.component';
import {ExchangeComponent} from './exchange/exchange.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {CurrencyService} from './_services/currency.service';
import {TradeService} from './_services/trade.service';
import {ActivityComponent} from './activity/activity.component';

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    routing
  ],
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    UserComponent,
    ExchangeComponent,
    EditUserComponent,
    ActivityComponent

  ],


  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    UserService,
    CurrencyService,
    TradeService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},


  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
