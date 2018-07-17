import {Currency} from ".";
import {User} from "src/app/_models/user";

export class Trade {
  id: number;
  user: User;
  currency: Currency;
  type: String; //either S(sell) or B (buy)
  amount: number;
  price: number;
  margin: number;
}
