import { BankAccount } from './bank-account';

export class User {
  public id?: number;
  public username?: string;
  public password?: string;
  public roles?: [];
  public bankAccount?: BankAccount;
}
