import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {User} from '../_models';
import {Wallet} from '../_models/wallet';
import {Trade} from 'src/app/_models/Trade';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }


  private BaseUrl = '/api';

  public getById(id: number) {
    return this.http.get<User>(this.BaseUrl + '/user=' + id);
  }

  public getAll() {

    return this.http.get<User[]>(this.BaseUrl + '/users');
  }

  public delete(id: number) {
    return this.http.delete(this.BaseUrl + "/user=" + id);
  }

  public deleteAllUsers() {
    return this.http.delete(this.BaseUrl + "/users");
  }

  public register(user) {
    return this.http.post<User>(this.BaseUrl + "/user", user);
  }

  public update(user) {
    return this.http.put<User>(this.BaseUrl + '/user', user);
  }

  public wallet(id: number) {
    return this.http.get<Wallet>(this.BaseUrl + '/user=' + id + '/wallet');
  }

  public getAllByUser(user_id: number) {

    return this.http.get<Trade[]>(this.BaseUrl + '/user=' + user_id + '/trades');
  }

}

// export class UserService {
//
//     constructor(private http: HttpClient) { }
//
//     getAll() {
//         return this.http.get<User[]>(`${environment.apiUrl}/users`);
//     }
//
//     getById(id: number) {
//         return this.http.get(`${environment.apiUrl}/users/` + id);
//     }
//
//     register(user: User) {
//         return this.http.post(`${environment.apiUrl}/users/register`, user);
//     }
//
//     update(user: User) {
//         return this.http.put(`${environment.apiUrl}/users/` + user.id, user);
//     }
//
//     delete(id: number) {
//         return this.http.delete(`${environment.apiUrl}/users/` + id);
//     }
// }
