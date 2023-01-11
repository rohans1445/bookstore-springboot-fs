import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { Book } from 'src/app/models/book.model';
import { ExchangeRequest } from 'src/app/models/ExchangeRequest.model';
import { AuthService } from 'src/app/services/auth.service';
import { BookService } from 'src/app/services/book.service';
import { ExchangeService } from 'src/app/services/exchange.service';
import { UserService } from 'src/app/services/user.service';
import { ToastParams } from 'src/app/toast/Toast.model';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {

  constructor(private exchangeService: ExchangeService,
    private auth: AuthService,
    private userService: UserService,
    private bookService: BookService) { }

  exchanges: ExchangeRequest[] = [];
  currentUser: string = this.auth.getCurrentLoggedInUsername();
  isCreatingNewExchange: boolean = false;
  userBooks: Book[] = [];
  allBooks: Book[] = [];
  exchangeForm: FormGroup = new FormGroup({})
  isError: boolean = false;
  // toastParams: ToastParams = {};

  ngOnInit(): void {
    this.getAllOpenExchanges();
    this.getAllUserBooks();
    this.getAllBooks();

    this.exchangeForm = new FormGroup({
      openerOwnedBookId: new FormControl(null, Validators.required),
      openerExchangeBookId: new FormControl(null, Validators.required)
    })
  }

  getAllOpenExchanges(){
    this.exchangeService.getAllOpenExchanges().subscribe({
      next: (res: ExchangeRequest[]) => {
        this.exchanges = res;
      }
    });
  }

  getAllUserBooks(){
    this.userService.getUsersBooks(this.auth.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.userBooks = res;
      }
    });
  }

  getAllBooks(){
    this.bookService.getAllBooks().subscribe({
      next: res => {
        this.allBooks = res;
      }
    });
  }

  onCloseModal(){
    this.isCreatingNewExchange = false;
    this.exchangeForm.reset();
  }

  onSubmit(){
    this.exchangeService.createExchange(this.exchangeForm.value).subscribe({
      next: res => {
        this.onCloseModal();
        this.getAllOpenExchanges();
      },
      error: (res:HttpErrorResponse) => {
        this.isError = true;
        setTimeout(() => {
          this.isError = false;
        }, 8000);
      }
    });
  }

  onCancelExchange(id: number){
    this.exchangeService.cancelExchange(id).subscribe({
      next: res => {
        this.getAllOpenExchanges();
      }
    });
  }

  processExchange(id: number){
    this.exchangeService.processExchange(id).subscribe({
      next: res => {
        this.getAllOpenExchanges();
      }
    });
  }

}
