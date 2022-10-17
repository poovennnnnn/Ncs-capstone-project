import { Component, OnInit } from '@angular/core';
import { Customer } from '../customer';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css'],
})
export class CustomerComponent implements OnInit {
  customerList?: Customer[];

  _filterText: string = '';
  filteredCustomer?: Customer[] = [];
  _sortText: string = '';

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
    // this.filteredCustomer = this.filterCustomer(value);
  }

  get sortText() {
    return this._filterText;
  }

  set sortText(value: string) {
    this._sortText = value;
  }

  constructor() {}

  ngOnInit(): void {
    this.filteredCustomer = this.customerList;
  }

  handleCreateAccount() {
    console.log('clicked in customer component');
  }

  handleEdit(id?: number) {
    console.log('clicked in customer component');
  }

  handleDelete(id?: number) {
    console.log('clicked in customer component');
  }

  filterCustomer(filterTerm: string) {
    // if (this.customerList.length == 0 || filterTerm == '') {
    //   return this.customerList;
    // } else {
    //   return this.customerList.filter((customer: Customer) => {
    //     console.log(filterTerm.toLowerCase());
    //     let filter = filterTerm.toLowerCase();
    //     let name = customer.customerName?.toLowerCase();
    //     let accountType = customer.accountType?.toLowerCase();
    //     let accountNumber = customer.accountNumber?.toString();
    //     return (
    //       name?.startsWith(filter) ||
    //       accountNumber?.startsWith(filter) ||
    //       accountType?.startsWith(filter)
    //     );
    //   });
    // }
  }

  //pagination
  onTableDataChange(event: any) {
    console.log(event);
    this.page = event;
    this.filteredCustomer = this.customerList;
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.filteredCustomer = this.customerList;
  }
}
