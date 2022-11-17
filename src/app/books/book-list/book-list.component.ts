import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { Book } from '../../models/book.model';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[] = [];
  isError: boolean = false;
  isLoading: boolean = true;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getAllBooks();
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

}
