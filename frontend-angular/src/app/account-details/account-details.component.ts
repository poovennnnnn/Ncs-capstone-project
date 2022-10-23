import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../model/bank-account';

import { BankAccountService } from '../_services/bank-account.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css'],
})
export class AccountDetailsComponent implements OnInit {
  bankAccount?: BankAccount;

  constructor(
    private authService: UserAuthService,
    private bankService: BankAccountService
  ) {}

  ngOnInit(): void {
    this.getBankAccount();
  }

  async getBankAccount() {
    this.bankAccount = await lastValueFrom(
      this.bankService.findById(this.authService!.getBankAccountId() as number)
    );
  }
}
