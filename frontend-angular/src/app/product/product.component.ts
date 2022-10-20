import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  products: any = [1, 2, 3, 4, 4, 45, 4, 4, 4, 4, 4];

  constructor() {}

  ngOnInit(): void {}
}
