import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../model/bank-account';
import { User } from '../model/user';

import { BankAccountService } from '../_services/bank-account.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  bankAccount?: BankAccount;
  active: string = '';
  constructor(
    private authService: UserAuthService,
    private bankService: BankAccountService
  ) {}

  ngOnInit(): void {
    this.getBankAccount();
  }

  async getBankAccount() {
    let user = this.authService.getUser() as User;
    let id = user.bankAccount?.id as number;
    this.bankAccount = (await lastValueFrom(
      this.bankService.findById(id)
    )) as BankAccount;
  }

  viewCustomerDetails() {
    this.active = 'view';
  }

  offers() {
    this.active = 'offer';
  }
}
