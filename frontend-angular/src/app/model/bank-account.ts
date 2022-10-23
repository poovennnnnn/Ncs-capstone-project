import { Customer } from './customer';

export class BankAccount {
  public id?: number;
  public actNumber?: number;
  public actType?: string;
  public actBalance?: number;
  public actCreationDate?: string;
  public customer?: Customer;
}
