import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../bank-account';
import { BankAccountService } from '../_services/bank-account.service';

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css'],
})
export class EditCustomerComponent implements OnInit {
  usernameFailed: boolean = false;
  firstnameFailed: boolean = false;
  lastnameFailed: boolean = false;
  cityfailed: boolean = false;
  phoneFailed: boolean = false;
  usernameError: string = 'some error';
  firstnameError: string = 'some error';
  lastnameError: string = 'some error';
  cityError: string = 'some error';
  phoneError: string = 'some error';

  flag: boolean = false;
  accountId?: number;
  accountType: string = '';
  form: any = {
    username: '',
    firstName: '',
    lastName: '',
    city: '',
    actType: '',
    phone: '',
    actBalance: '',
    actNumber: '',
  };
  constructor(
    private bankService: BankAccountService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((data) => {
      this.accountId = data['accountId'];
    });

    this.fetchAccount();
  }

  async fetchAccount() {
    let response = (await lastValueFrom(
      this.bankService.findById(this.accountId as number)
    ).catch((err) => {
      console.log(err);
    })) as BankAccount;
    this.form.firstName = response.customer?.firstName;
    this.form.lastName = response.customer?.lastName;
    this.form.city = response.customer?.city;
    this.form.phone = response.customer?.phone;
    this.form.accountType = response.actType;
    this.form.actBalance = response.actBalance;
    this.form.actNumber = response.actNumber;
  }

  async createAccount(f: NgForm) {
    if (!this.isFormvalid()) {
      f.resetForm();
    }

    console.log(typeof this.form.actBalance);

    console.log(this.form);

    let response = await lastValueFrom(
      this.bankService.update(this.form)
    ).catch((err) => {
      console.log(err);
    });

    if (response != null) {
      this.router.navigate(['admin']);
    }

    console.log(response);
  }

  validate() {
    if (Number.isInteger(parseInt(this.form.username.charAt(0)))) {
      this.usernameFailed = true;
      this.usernameError = 'First Letter of the Username cannot contain digit';
      // return false;
    } else {
      this.usernameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.firstName.charAt(0)))) {
      this.firstnameFailed = true;
      this.firstnameError = 'First Letter cannot contain digit';
      // return false;
    } else {
      this.firstnameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.lastName.charAt(0)))) {
      this.lastnameFailed = true;
      this.lastnameError = 'First Letter of lastname cannot contain digit';
      // return false;
    } else {
      this.lastnameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.city.charAt(0)))) {
      this.cityfailed = true;
      this.cityError = 'First Letter of city cannot contain digit';
      // return false;
    } else {
      this.cityfailed = false;
    }
    // if (!Number.isInteger(parseInt(this.form.phone))) {
    //   this.phoneFailed = true;
    //   this.phoneError = 'this field Should only contain Number';
    //   // return false;
    // } else {
    //   this.phoneFailed = false;
    //   // this.phoneFailed = true;
    //   // this.phoneError = 'this field Should only contain Number';
    // }
  }

  validatePhone() {
    if (!Number(this.form.phone)) {
      this.phoneFailed = true;
      this.phoneError = 'this field Should only contain Number';
      // return false;
    } else {
      this.phoneFailed = false;
      // this.phoneFailed = true;
      // this.phoneError = 'this field Should only contain Number';
    }
  }
  isFormvalid() {
    if (
      !this.usernameFailed &&
      !this.firstnameFailed &&
      !this.lastnameFailed &&
      !this.cityfailed &&
      !this.phoneFailed
    ) {
      return true;
    }
    return false;
  }

  close() {
    this.router.navigate(['admin/show']);
  }
}
