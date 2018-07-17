import {Component, OnInit} from '@angular/core';
import {NavbarService} from '../_services';
import {User} from '../_models';
import {Trade} from 'src/app/_models/Trade';
import {UserService} from 'src/app/_services/user.service';
import {TradeService} from '../_services/trade.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {
  user: User;
  public Trades: Trade[];

  constructor(
    private nav: NavbarService,
    private router: Router,
    private userService: UserService,
    private tradeService: TradeService
  ) {
  }

  ngOnInit() {
    this.nav.hide();
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.userService.getAllByUser(this.user.id).subscribe(data => {
      this.Trades = data;
      console.log(this.Trades);
    });

  }

  closeTrade(id: number) {

    this.tradeService.closeTrade(id).subscribe(data =>

      this.userService.getAllByUser(this.user.id).subscribe(data =>
        this.Trades = data))


  }
}
