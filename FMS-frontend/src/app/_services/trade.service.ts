import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Trade} from 'src/app/_models/Trade';

@Injectable({
  providedIn: 'root'
})
export class TradeService {

  constructor(private http: HttpClient) {
  }

  private BaseUrl = '/api';

  public getById(id: number) {
    return this.http.get<Trade>(this.BaseUrl + '/trade=' + id);
  }

  public getAll() {

    return this.http.get<Trade[]>(this.BaseUrl + '/trades');
  }

  public getAllByUser(user_id: number) {

    return this.http.get<Trade[]>(this.BaseUrl + "/user=" + user_id + '/trades');
  }

  public closeTrade(id) {

    return this.http.post(this.BaseUrl + "/closeTrade=" + id, null);
  }

  public delete(id: number) {
    return this.http.delete(this.BaseUrl + "/trade=" + id);
  }

  public deleteAllOperations() {
    return this.http.delete(this.BaseUrl + "/trades");
  }

  public create(user_id: number, currency_id: number, Trade) {
    return this.http.post<Trade>(
      this.BaseUrl + "/user=" + user_id + "/currency=" + currency_id + "/trade", Trade);
  }

  public update(Trade) {
    return this.http.put<Trade>(this.BaseUrl + '/currency', Trade);
  }

}
