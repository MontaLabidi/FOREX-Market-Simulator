import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home';
import {LoginComponent} from './login';
import {RegisterComponent} from './register';
import {AuthGuard} from './_guards';
import {RegGuard} from 'src/app/_guards/reg.guard';
import {UserComponent} from './user/user.component';
import {ExchangeComponent} from './exchange/exchange.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {ActivityComponent} from './activity/activity.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'user', component: UserComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent, canActivate: [RegGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [RegGuard]},
  {path: 'exchange', component: ExchangeComponent},
  {path: 'editUser', component: EditUserComponent},
  {path: 'activity', component: ActivityComponent},

  // otherwise redirect to home
  {path: '**', redirectTo: ''}
];

export const routing = RouterModule.forRoot(appRoutes);
