import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../model/bank-account';

import { BankAccountService } from '../_services/bank-account.service';
import { DataServiceService } from '../_services/data-service.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
})
export class CreateAccountComponent implements OnInit {
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

  accountType: string = '';
  form: any = {
    username: '',
    password: '',
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
    private dataService: DataServiceService
  ) {}

  ngOnInit(): void {}

  async createAccount(f: NgForm) {
    if (!this.isFormvalid()) {
      f.resetForm();
    }

    console.log(typeof this.form.actBalance);

    console.log(this.form);

    this.form.password = f.value['password'];
    console.log(f);
    console.log(this.form.password);

    let response = (await lastValueFrom(
      this.bankService.create(this.form)
    ).catch((err) => {
      console.log(err);
    })) as BankAccount;

    if (response != null) {
      this.form.actNumber = response.actNumber;
      this.dataService.response = this.form;
      this.router.navigate(['admin/success']);
    }
  }

  validate() {
    if (Number.isInteger(parseInt(this.form.username.charAt(0)))) {
      this.usernameFailed = true;
      this.usernameError = 'First Letter of the Username cannot contain digit';
    } else {
      this.usernameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.firstName.charAt(0)))) {
      this.firstnameFailed = true;
      this.firstnameError = 'First Letter cannot contain digit';
    } else {
      this.firstnameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.lastName.charAt(0)))) {
      this.lastnameFailed = true;
      this.lastnameError = 'First Letter of lastname cannot contain digit';
    } else {
      this.lastnameFailed = false;
    }

    if (Number.isInteger(parseInt(this.form.city.charAt(0)))) {
      this.cityfailed = true;
      this.cityError = 'First Letter of city cannot contain digit';
    } else {
      this.cityfailed = false;
    }
  }

  validatePhone() {
    if (!Number(this.form.phone)) {
      this.phoneFailed = true;
      this.phoneError = 'this field Should only contain Number';
    } else {
      this.phoneFailed = false;
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
}
