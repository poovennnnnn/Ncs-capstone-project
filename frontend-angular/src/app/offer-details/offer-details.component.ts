import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Offers } from '../offers';
import { BankAccountService } from '../_services/bank-account.service';
import { DataServiceService } from '../_services/data-service.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css'],
})
export class OfferDetailsComponent implements OnInit {
  offer?: Offers;

  @Input() name = '';
  @Output() testEvent = new EventEmitter();

  constructor(
    private bankService: BankAccountService,
    private userAuthService: UserAuthService,
    private router: Router,
    private route: ActivatedRoute,
    private dataService: DataServiceService
  ) {}

  ngOnInit(): void {
    // this.route.queryParams.subscribe((data) => {
    //   this.offerList = data['offer'];
    // });
    this.offer = this.dataService.getOffer();
  }

  async handleClick() {
    console.log('clicked in offfer details component');
    let response = (await lastValueFrom(
      this.bankService.createOffer(
        this.offer as Offers,
        this.userAuthService.getBankAccountId() as number
      )
    ).catch((err) => {
      console.log(err);
    })) as string;

    console.log(response);
    this.close();
  }

  close() {
    // this.testEvent.emit(true);
    // console.log('clicked');
    this.router.navigate(['user/offer']);
  }
}
