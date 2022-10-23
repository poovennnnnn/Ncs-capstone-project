import { Injectable } from '@angular/core';
import { Offers } from '../model/offers';

@Injectable({
  providedIn: 'root',
})
export class DataServiceService {
  public offer?: Offers;
  public response: any;

  constructor() {}

  setOffer(offer: Offers) {
    this.offer = offer;
  }

  getOffer(): Offers {
    return this.offer as Offers;
  }
}
