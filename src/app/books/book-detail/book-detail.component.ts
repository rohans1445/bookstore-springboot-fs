import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Book } from 'src/app/book.model';
import { BookService } from 'src/app/book.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit, OnDestroy {
  book!: Book;
  isLoading: Boolean = true;
  isError: Boolean = false;
  getBookSubscription: Subscription = new Subscription;

  constructor(private bookService: BookService,
    private router: Router,
    private currentRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.currentRoute.params.subscribe({
      next: (param: Params)=>{
        this.getBookSubscription = this.bookService.getBookById(param['id']).subscribe({
          next: (response: Book)=>{
            this.book = response;
            this.isLoading = false;
            console.log(response);
          },
          error: (error: HttpErrorResponse)=>{
            this.isError = true;
            this.isLoading = false;
          }
        });
      }
    })
  }

  ngOnDestroy(): void {
    this.getBookSubscription.unsubscribe();
  }

}
