import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { Book } from '../../../models/book.model';
import { BookService } from '../../../services/book.service';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[] = [];
  isError: boolean = false;
  isLoading: boolean = true;
  isAdding: boolean = false;
  toastMessage: string = '';
  toastType: string = '';
  toastDisplay: boolean = false;

  constructor(private bookService: BookService,
    private currentRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAllBooks();
    this.currentRoute.queryParamMap.subscribe((paramMap: ParamMap) => {
      if(paramMap.get('orderSuccess') !== null){
        this.toastMessage = 'Order placed successfully!';
        this.toastType = 'success';
        this.toastDisplay = true;
        setTimeout(() => {
          this.toastDisplay = false;
        }, 5000);
      }
    })
  }

  getAllBooks(){
    this.bookService.getBooks().subscribe({
      next: (response: Book[])=>{
        this.books = response;
        this.isError = false;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse)=>{
        this.isError = true;
        this.isLoading = false;
      }
    })
  }

  onCloseModal(){
    this.isAdding = false;
  }

  onOpenModal() {
    this.isAdding = true;
    // window.scrollTo(0, 0);
  }

}
