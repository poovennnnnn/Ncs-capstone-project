import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { User } from '../user';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLoggedIn: boolean = false;
  isLoginFailed: boolean = false;
  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.userAuthService.isLoggedIn()) {
      let role = this.userAuthService.getRoles()[0].roleName;

      if (role === 'ROLE_ADMIN') {
        this.router.navigate(['/admin/create']);
      } else {
        this.router.navigate(['/user/account']);
      }
    }
  }

  async login(loginForm: NgForm) {
    let username = loginForm.value['username'] as string;
    let password = loginForm.value['password'] as string;
    if (!this.validate(username, password)) {
      this.isLoginFailed = true;

      throw new Error('Something went wrong');
    }
    // console.log(Number.isInteger(parseInt(username.charAt(0))));

    // if (Number.isInteger(parseInt(username.charAt(0)))) {
    //   console.log('error');
    //   this.errorMessage = 'First letter of username should not Contain digit';
    //   this.isLoginFailed = true;
    // }

    let response = (await lastValueFrom(
      this.userService.login(loginForm.value)
    ).catch((error) => {
      console.log(error);
      this.isLoginFailed = true;
    })) as any;

    console.log(response);
    this.userAuthService.saveUser(response.user as User);
    this.userAuthService.setRoles(response.user.role);
    this.userAuthService.setToken(response.token);
    this.isLoggedIn = true;
    console.log(response.user.role);

    this.ngOnInit();
  }

  validate(username: string, password: string) {
    console.log(Number.isInteger(parseInt(username.charAt(0))));
    let regex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(
        password
      );

    if (Number.isInteger(parseInt(username.charAt(0)))) {
      console.log('error');
      this.errorMessage = 'First letter of username should not Contain digit';
      this.isLoginFailed = true;
      return false;
    } else if (!regex) {
      this.isLoginFailed = true;
      this.errorMessage =
        'Minimum eight characters, at least one letter, one number and one special character:';
      return false;
    }

    return true;
  }
}
