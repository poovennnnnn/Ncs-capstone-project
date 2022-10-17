import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BankAccountService {
  PATH_OF_API = 'http://localhost:8080/account';

  constructor(private http: HttpClient) {}

  public create(data: any) {
    return this.http.post(this.PATH_OF_API + '/create', data);
  }

  public update(data: any) {
    return this.http.post(this.PATH_OF_API + '/update', data);
  }

  public findAll() {
    return this.http.get(this.PATH_OF_API);
  }

  public findById(id: number) {
    return this.http.get(this.PATH_OF_API + '/' + id);
  }

  public deleteById(id: number) {
    return this.http.delete(this.PATH_OF_API + '/' + id);
  }
}
