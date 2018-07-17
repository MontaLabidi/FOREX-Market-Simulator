import {Component, OnInit} from '@angular/core';
import {User} from '../_models';
import {UserService} from '../_services';
import {NavbarService} from 'src/app/_services/navbar.service';
import {Router} from '@angular/router';
import {AuthenticationService} from 'src/app/_services/authentication.service';
import {AlertService} from 'src/app/_services/alert.service';
import {Wallet} from '../_models/wallet';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  date: any;
  user: User;
  wallet: Wallet;
  keys = [];

  constructor(private router: Router,
              private userService: UserService,
              public nav: NavbarService,
              private auth: AuthenticationService,
              private alertService: AlertService) {


  }

  ngOnInit() {

    this.nav.hide();
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.userService.wallet(this.user.id).subscribe(
      wallet => {
        this.wallet = wallet;
      }
    );


  }

  delete(user: User): void {
    if (window.confirm('Delete this Account?')) {
      this.userService.delete(user.id)
        .subscribe(user => {
            this.auth.logout();
          },
          error => {
            this.alertService.error(error);

          });
    }


  }

  editUser(user: User): void {
    localStorage.removeItem("currentUser");
    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate(['editUser']);
  };


}
