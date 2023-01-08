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

  toastType: string = '';
  showToast: boolean = false;
  toastMessage: string = '';
  cart: Book[] = [];
  total: number = 0;

  ngOnInit(): void {
    this.cartService.getUserCart().subscribe({
      next: res => {
        this.cart = res;
        this.calculateTotal(this.cart);
      }
    });
  }

  onRemoveFromCart(id: number) {
    this.cartService.removeFromCart(id).subscribe({
      next: res => {
        this.ngOnInit();
        this.toastType='success';
        this.toastMessage = 'Removed item from cart';
        this.showToast = true;
        setTimeout(() => {
          this.showToast = false;
        }, 3000);
        this.cartService.cartUpdated.next(true);
      }
    });
  }

  calculateTotal(cart: Book[]){
    this.total = 0;
    for(let item of cart){
      this.total += item.price;
    }
  }

}
