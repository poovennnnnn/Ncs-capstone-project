import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../model/bank-account';
import { Offers } from '../model/offers';

import { BankAccountService } from '../_services/bank-account.service';
import { DataServiceService } from '../_services/data-service.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css'],
})
export class OfferComponent implements OnInit {
  bankAccount?: BankAccount;
  creditCard?: Offers;
  homeLoan?: Offers;
  carLoan?: Offers;

  active: string = '';
  name: string = '';

  offers?: Offers[];
  flag: boolean = true;

  constructor(
    private router: Router,
    private userAuthService: UserAuthService,
    private bankService: BankAccountService,
    private dataService: DataServiceService
  ) {}

  ngOnInit(): void {
    this.getBankAccount();
    this.getAllOffer();

    // this.flag = true;
  }

  async getBankAccount() {
    this.bankAccount = (await lastValueFrom(
      this.bankService.findById(
        this.userAuthService.getBankAccountId() as number
      )
    ).catch((err) => {
      console.log(err);
    })) as BankAccount;
  }

  async getAllOffer() {
    this.offers = (await lastValueFrom(
      this.bankService.getAllOffer(
        this.userAuthService.getBankAccountId() as number
      )
    ).catch((err) => {
      console.log('Err in offer ' + err);
    })) as Offers[];

    console.log(this.offers);
  }

  handleCreditCard(offer: Offers) {
    this.dataService.setOffer(offer);
    this.router.navigate(['user/offer/details']);
  }
}
