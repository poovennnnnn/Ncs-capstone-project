import { Component } from '@angular/core';
import { BankAccountService } from './_services/bank-account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'demo-ws3';

  constructor(private bankService: BankAccountService) {}

  // ngOnInit() {

  //   this.bankService.findById()
  // }
}
