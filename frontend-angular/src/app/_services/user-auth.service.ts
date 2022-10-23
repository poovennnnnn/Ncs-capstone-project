import { Injectable } from '@angular/core';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor() {}

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles() {
    return JSON.parse(localStorage.getItem('roles') as string);
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken') as string;
  }

  public saveUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser(): User {
    return JSON.parse(localStorage.getItem('user') as string);
  }

  public getBankAccountId() {
    return this.getUser().bankAccount?.id;
  }

  public getUserId() {
    return this.getUser().id;
  }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    // return this.getRoles() && this.getToken();
    return this.getRoles() != null && this.getToken() != null;
  }
}
