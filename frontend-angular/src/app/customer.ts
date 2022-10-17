import { Offers } from './offers';

export class Customer {
  public id?: number;
  public firstName?: string;
  public lastName?: string;
  public city?: string;
  public phone?: number;
  public offers?: Offers[];
}
