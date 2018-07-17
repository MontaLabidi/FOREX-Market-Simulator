import {Component, OnInit} from '@angular/core';
import {NavbarService} from '../_services';
import {AuthenticationService} from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private auth: AuthenticationService, private nav: NavbarService) {


  }


  ngOnInit() {

  }

}
