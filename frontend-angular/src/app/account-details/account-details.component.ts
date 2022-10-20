import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../bank-account';
import { User } from '../user';
import { BankAccountService } from '../_services/bank-account.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css'],
})
export class AccountDetailsComponent implements OnInit {
  // customerName: string = 'Scott';
  // accountType: string = 'Savings';
  // accountNo: number = 234234324;
  // accountBalance: number = 3000;
  bankAccount?: BankAccount;

  constructor(
    private authService: UserAuthService,
    private userService: UserService,
    private bankService: BankAccountService
  ) {}

  ngOnInit(): void {
    // let id = user.bankAccount?.id as number;

    this.bankService
      .findById(this.authService.getBankAccountId() as number)
      .subscribe(
        (response) => {
          this.bankAccount = response;
          console.log(this.bankAccount);
        },
        (err) => {
          console.log(err);
        }
      );
  }
}
