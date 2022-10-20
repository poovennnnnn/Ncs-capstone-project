import { Component, OnInit } from '@angular/core';
import { DataServiceService } from '../_services/data-service.service';

@Component({
  selector: 'app-success-response',
  templateUrl: './success-response.component.html',
  styleUrls: ['./success-response.component.css'],
})
export class SuccessResponseComponent implements OnInit {
  form: any;
  constructor(private dataService: DataServiceService) {}

  ngOnInit(): void {
    this.form = this.dataService.response;
  }
}
