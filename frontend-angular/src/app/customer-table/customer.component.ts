import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { BankAccount } from '../bank-account';
import { BankAccountService } from '../_services/bank-account.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css'],
})
export class CustomerComponent implements OnInit {
  bankAccountList: BankAccount[] = [];

  _filterText: string = '';
  filteredList: BankAccount[] = [];
  _sortText: string = '';
  accountId?: number;

  //pagination variables
  page: number = 1;
  count: number = 0;
  tableSize: number = 6;
  tableSizes: any = [3, 6, 9, 12];

  get filterText() {
    return this._filterText;
  }

  set filterText(value: string) {
    this._filterText = value;

    this.filteredList = this.filterCustomer(value);
    console.log(value);
  }

  get sortText() {
    return this._filterText;
  }

  set sortText(value: string) {
    this._sortText = value;
  }

  constructor(
    private authService: UserAuthService,
    private userService: UserService,
    private bankService: BankAccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllBankaccount();
  }

  getAllBankaccount() {
    this.bankService.findAll().subscribe(
      (response) => {
        console.log(response as BankAccount[]);
        this.bankAccountList = response as BankAccount[];
        console.log(this.bankAccountList);
        this.filteredList = this.bankAccountList;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  handleCreateAccount() {
    console.log('clicked in customer component');
  }

  handleEdit(id?: number) {
    console.log('clicked in customer component');
    // this.router.navigate(['/login'], {
    //   queryParams: { targetUrl: route.url },
    // });
    this.router.navigate(['admin/edit'], {
      queryParams: { accountId: id },
    });
  }

  async handleDelete() {
    console.log('clicked in customer component');
    let response = (await lastValueFrom(
      this.bankService.deleteById(this.accountId as number)
    ).catch((err) => {
      console.log(err);
    })) as string;

    console.log(response);

    this.ngOnInit();
  }

  filterCustomer(filterTerm: string) {
    return (this.filteredList = this.bankAccountList.filter((data) => {
      let filter = filterTerm.toLowerCase();
      let accountType = data.actType?.toLowerCase();
      let accountNumber = data.actNumber?.toString().toLocaleLowerCase();
      let firstName = data.customer?.firstName?.toLocaleLowerCase();
      let lastName = data.customer?.lastName?.toLocaleLowerCase();
      let city = data.customer?.city?.toLocaleLowerCase();
      let phone = data.customer?.phone?.toString().toLocaleLowerCase();

      return (
        accountType?.startsWith(filter) ||
        accountNumber?.startsWith(filter) ||
        firstName?.startsWith(filter) ||
        lastName?.startsWith(filter) ||
        city?.startsWith(filter) ||
        phone?.startsWith(filter)
      );
    }));
  }

  //pagination
  onTableDataChange(event: any) {
    console.log(event);
    this.page = event;
    this.filteredList = this.bankAccountList;
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.filteredList = this.bankAccountList;
  }

  bankId(id?: number) {
    console.log('hellooooooooo');
    this.accountId = id;
  }

  edit() {
    console.log('clicked');
  }
}
