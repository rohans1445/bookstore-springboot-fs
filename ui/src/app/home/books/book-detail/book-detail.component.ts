import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Book } from 'src/app/models/book.model';
import { AuthService } from 'src/app/services/auth.service';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit, OnDestroy {
  book: Book = new Book();
  isLoading: boolean = true;
  isError: boolean = false;
  isEditing: boolean = false;
  getBookSubscription: Subscription = new Subscription;

  constructor(private bookService: BookService,
    private router: Router,
    private currentRoute: ActivatedRoute,
    public authService: AuthService) { }

  ngOnInit(): void {
    this.currentRoute.params.subscribe({
      next: (param: Params)=>{
        this.getBookSubscription = this.bookService.getBookById(param['id']).subscribe({
          next: (response: Book)=>{
            this.book = response;
            this.isLoading = false;
          },
          error: (error: HttpErrorResponse)=>{
            this.isError = true;
            this.isLoading = false;
          }
        });
      }
    })
  }

  onCloseModal(){
    this.isEditing = false;
    this.ngOnInit();
  }

  ngOnDestroy(): void {
    this.getBookSubscription.unsubscribe();
  }

}