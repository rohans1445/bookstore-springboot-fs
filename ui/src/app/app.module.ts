import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BookListComponent } from './home/books/book-list/book-list.component';
import { BookDetailComponent } from './home/books/book-detail/book-detail.component';
import { ErrorComponent } from './error/error.component';
import { FooterComponent } from './footer/footer.component';
import { BooksComponent } from './home/books/books.component';
import { ReviewsComponent } from './home/books/book-detail/reviews/reviews.component';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';
import { BookFormComponent } from './home/books/book-form/book-form.component';
import { HomeComponent } from './home/home.component';
import { ModalComponent } from './modal/modal.component';
import { ToastComponent } from './toast/toast.component';
import { CartComponent } from './home/cart/cart.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookListComponent,
    BookDetailComponent,
    ErrorComponent,
    FooterComponent,
    BooksComponent,
    ReviewsComponent,
    AuthComponent,
    LoginComponent,
    RegisterComponent,
    LoaderComponent,
    BookFormComponent,
    HomeComponent,
    ModalComponent,
    ToastComponent,
    CartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }