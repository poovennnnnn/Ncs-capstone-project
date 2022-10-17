import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../bank-account';
import { BankAccountService } from '../_services/bank-account.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  bankAccount?: BankAccount;
  str: string = '';
  active: string = '';
  constructor(
    private authService: UserAuthService,
    private userService: UserService,
    private bankService: BankAccountService
  ) {}

  ngOnInit(): void {
    this.active = 'view';
    let user = this.authService.getUser();
    let id = user.bankAccount.id;
    console.log(id);
    this.bankService.findById(id).subscribe(
      (response) => {
        this.bankAccount = response;
        // console.log(this.bankAccount);
      },
      (err) => {
        // console.log(err);
      }
    );
  }

  async handleClick() {
    // lastValueFrom(this.userService.forAdmin());
    // this.str = (await lastValueFrom(this.userService.forAdmin())) as string;
    // console.log(this.str);
    let user = this.authService.getUser();
    let id = user.bankAccount.id;
    console.log(id);
    this.bankService.findById(id).subscribe(
      (response) => {
        console.log(response);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  viewCustomerDetails() {
    this.active = 'view';
  }

  fundTransfer() {
    this.active = 'fund';
  }

  offers() {
    this.active = 'offers';
  }
}
