import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { Book } from '../../../models/book.model';
import { BookService } from '../../../services/book.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Form, NgForm } from '@angular/forms';
import { BookList } from 'src/app/models/BookList.model';
import { PagedResponse } from 'src/app/models/PagedResponse.model';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: BookList[] = [];
  isError: boolean = false;
  isLoading: boolean = true;
  isAdding: boolean = false;
  toastMessage: string = '';
  toastType: string = '';
  toastDisplay: boolean = false;
  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = -1;
  isLast: boolean = false;
  totalResults: number = 0;
  currentUser!: User;

  constructor(private bookService: BookService,
    private currentRoute: ActivatedRoute,
    private auth: AuthService) { }

  ngOnInit(): void {
    this.getAllBooks(this.currentPage, this.pageSize);
    this.currentUser = this.auth.getCurrentUser();
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

    this.bookService.getBooksPaged(1,10).subscribe({
      next: res => {
        console.log(res);
      }
    });
  }

  getAllBooks(page: number, size: number){
    this.isLoading = true;
    this.bookService.getBooksPaged(page, size).subscribe({
      next: (res: PagedResponse<BookList>)=>{
        this.books = res.content;
        this.totalPages = res.totalPages;
        this.isLast = res.last;
        this.totalResults = res.totalElements;
        this.isError = false;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse)=>{
        this.isError = true;
        this.isLoading = false;
      }
    })
  }

  selectPage(page: number){
    if(this.isLoading || page === this.currentPage) return;
    this.currentPage = page;
    this.getAllBooks(this.currentPage, this.pageSize);
  }

  nextPage(){
    if(this.isLoading) return;
    if(this.isLast) return;
    this.getAllBooks(++this.currentPage, this.pageSize);
  }

  previousPage(){
    if(this.isLoading) return;
    if(this.currentPage === 1) return;
    this.getAllBooks(--this.currentPage, this.pageSize);
  }

  onCloseModal(){
    this.isAdding = false;
  }

  onOpenModal() {
    this.isAdding = true;
    // window.scrollTo(0, 0);
  }

  onSearchSubmit(form: NgForm){
    this.bookService.searchBook(form.value.search).subscribe({
      next: res => {
        this.books = res;
      }
    });
  }

}
