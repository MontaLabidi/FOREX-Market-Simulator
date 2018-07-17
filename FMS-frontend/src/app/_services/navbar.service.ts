import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../_models';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {
  show: boolean = true;
  user: User;
  username: String;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  hide() {
    console.log("bar hided");
    this.show = false;
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.username = this.user.username;
  }

  showlog() {
    console.log("bar showed");
    this.show = true;
  }


  //   showlog() {
  //     this.router.events.subscribe((url:any) =>{
  //       if (this.router.url === '/login'){
  //
  //             this.show=true;
  //               console.log(this.show);
  //               console.log("called");
  //           }});
  //     //  console.log(this.router.url);
  // }

}
