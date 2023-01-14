import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { Book } from 'src/app/models/book.model';
import { ExchangeRequest } from 'src/app/models/ExchangeRequest.model';
import { AuthService } from 'src/app/services/auth.service';
import { BookService } from 'src/app/services/book.service';
import { ExchangeService } from 'src/app/services/exchange.service';
import { ToastService } from 'src/app/services/toast.service';
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
    private bookService: BookService,
    private toast: ToastService) { }

  exchanges: ExchangeRequest[] = [];
  currentUser: string = this.auth.getCurrentLoggedInUsername();
  isCreatingNewExchange: boolean = false;
  userBooks: Book[] = [];
  allBooks: Book[] = [];
  exchangeForm: FormGroup = new FormGroup({})
  isError: boolean = false;

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
        this.toast.showToast('Exchange created', 'Your exchange has been created', 'success');
        this.getAllOpenExchanges();
      },
      error: (res:HttpErrorResponse) => {
        this.toast.showToast('Error', 'Could not create exchange', 'error');
      }
    });
  }

  onCancelExchange(id: number){
    this.exchangeService.cancelExchange(id).subscribe({
      next: res => {
        this.getAllOpenExchanges();
        this.toast.showToast('Success', 'Exchange request cancelled', 'success');
      },
      error: res => {
        this.toast.showToast('Error', 'Could not cancel', 'error');
      }
    });
  }

  processExchange(id: number){
    this.exchangeService.processExchange(id).subscribe({
      next: res => {
        this.getAllOpenExchanges();
        this.toast.showToast('Success', 'Books exchanged', 'success');
      },
      error: res => {
        this.toast.showToast('Error', 'Error exchanging', 'error');
      }
    });
  }

}
