import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  constructor(private cartService: CartService) { }

  cart: Book[] = [];

  ngOnInit(): void {
    this.cartService.getUserCart().subscribe({
      next: res => {
        this.cart = res;
      }
    });
  }

}
