import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Book } from '../../book.model';
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
    console.log("book list compoennt initialized")
    this.getAllBooks();
  }

  getAllBooks(){
    console.log("get all books called");
    this.bookService.getBooks().subscribe({
      next: (response: Book[])=>{
        console.log("In next block booklist component");
        this.books = response;
        this.isError = false;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse)=>{
        console.log("In error block booklist component");
        this.isError = true;
        this.isLoading = false;
      }
    })
  }

}
