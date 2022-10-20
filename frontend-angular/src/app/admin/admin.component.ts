import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  str: string = '';
  active: string = '';
  constructor(private userService: UserService) {}

  ngOnInit(): void {}

  // async handleClick() {
  //   lastValueFrom(this.userService.forAdmin());
  //   this.str = (await lastValueFrom(this.userService.forAdmin())) as string;
  //   console.log(this.str);
  // }

  createCustomer() {
    this.active = 'create';
  }

  showAllCustomer() {
    this.active = 'show';
  }
}
