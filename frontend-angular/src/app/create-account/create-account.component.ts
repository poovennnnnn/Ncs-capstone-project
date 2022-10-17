import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
})
export class CreateAccountComponent implements OnInit {
  isCreateAccountFailed: boolean = false;
  flag: boolean = false;
  accountType: string = '';
  constructor() {}

  ngOnInit(): void {}

  createAccount(f: NgForm) {
    console.log('clicked');
    console.log(f.value);
    console.log(this.accountType);
  }
}
