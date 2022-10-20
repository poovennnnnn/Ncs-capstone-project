import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { AdminComponent } from './admin/admin.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { CustomerComponent } from './customer-table/customer.component';
import { EditCustomerComponent } from './edit-customer/edit-customer.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { OfferDetailsComponent } from './offer-details/offer-details.component';
import { OfferComponent } from './offer/offer.component';
import { ProductComponent } from './product/product.component';
import { SuccessResponseComponent } from './success-response/success-response.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './_auth/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_ADMIN'] },
    children: [
      {
        path: 'create',
        component: CreateAccountComponent,
      },
      {
        path: 'show',
        component: CustomerComponent,
      },
      {
        path: 'edit',
        component: EditCustomerComponent,
      },
      {
        path: 'success',
        component: SuccessResponseComponent,
      },
    ],
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_USER'] },
    children: [
      {
        path: 'account',
        component: AccountDetailsComponent,
      },
      {
        path: 'offer',
        component: OfferComponent,
      },
      { path: 'offer/details', component: OfferDetailsComponent },
    ],
  },
  { path: 'about', component: AboutComponent },
  { path: 'product', component: ProductComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forbidden', component: ForbiddenComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
