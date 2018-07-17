import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Currency} from '../_models';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  currentCurrency: Currency;

  constructor(private http: HttpClient) {
  }

  private BaseUrl = '/api';

  public getById(id: number) {
    return this.http.get<Currency>(this.BaseUrl + '/currency=' + id);
  }

  public getAll() {

    return this.http.get<Currency[]>(this.BaseUrl + '/currencies');
  }

  public delete(id: number) {
    return this.http.delete(this.BaseUrl + "/currency=" + id);
  }

  public deleteAllCurrencys() {
    return this.http.delete(this.BaseUrl + "/currencies");
  }

  public register(Currency) {
    return this.http.post<Currency>(this.BaseUrl + "/currency", Currency);
  }

  public update(Currency) {
    return this.http.put<Currency>(this.BaseUrl + '/currency', Currency);
  }


}
