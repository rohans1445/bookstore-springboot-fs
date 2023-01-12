import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Book } from 'src/app/models/book.model';
import { BookDetail } from 'src/app/models/bookDetail.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { BookService } from 'src/app/services/book.service';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit{
  book!: Book;
  isLoading: boolean = true;
  isError: boolean = false;
  isEditing: boolean = false;
  toastMessage: string = '';
  toastType: string = '';
  toastDisplay: boolean = false;
  bookOwned: boolean = false;
  currentUser!: User;

  constructor(private bookService: BookService,
    private router: Router,
    private currentRoute: ActivatedRoute,
    public authService: AuthService,
    private cartService: CartService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.currentRoute.params.subscribe({
      next: (param: Params)=>{
        this.getBookById(param['id']);
        this.checkOwnership(this.authService.currentUser.username!, param['id']);
      }
    })
  }

  onCloseModal(){
    this.isEditing = false;
    this.ngOnInit();
  }

  getBookById(id: number){
    this.bookService.getBookById(id).subscribe({
      next: (response: Book)=>{
        this.book = response;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse)=>{
        this.isError = true;
        this.isLoading = false;
        this.router.navigate(['/not-found']);
      }
    });
  }

  checkOwnership(username: string, id: number){
    this.userService.itemOwnedByUser(username, id).subscribe({
      next: res => {
        this.bookOwned = true;
      },
      error: res => {
        this.bookOwned = false;
      }
    });
  }

  onAddToCart(){
    this.cartService.addToCart({bookId: this.currentRoute.snapshot.paramMap.get('id')}).subscribe({
      next: res => {
        this.toastDisplay = true;
        this.toastMessage = 'Added book to cart';
        this.toastType = 'success';
        setTimeout(() => {
          this.toastDisplay = false;
        }, 3000);
        this.cartService.cartUpdated.next(true);
      },
      error: (error: HttpErrorResponse)=>{
        this.toastDisplay = true;
        this.toastMessage = 'Book is already in cart!';
        this.toastType = 'danger';
        setTimeout(() => {
          this.toastDisplay = false;
        }, 3000);
      }
    });
  }

}
