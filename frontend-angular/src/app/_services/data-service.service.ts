import { Injectable } from '@angular/core';
import { Offers } from '../offers';

@Injectable({
  providedIn: 'root',
})
export class DataServiceService {
  public offer?: Offers;

  constructor() {}

  setOffer(offer: Offers) {
    this.offer = offer;
  }

  getOffer(): Offers {
    return this.offer as Offers;
  }
}
