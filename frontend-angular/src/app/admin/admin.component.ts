import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  active: string = '';
  constructor() {}

  ngOnInit(): void {}
  createCustomer() {
    this.active = 'create';
  }

  showAllCustomer() {
    this.active = 'show';
  }
}
