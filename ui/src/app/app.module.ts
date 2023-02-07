import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'

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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';
import { BookFormComponent } from './home/books/book-form/book-form.component';
import { HomeComponent } from './home/home.component';
import { ModalComponent } from './modal/modal.component';
import { ToastComponent } from './toast/toast.component';
import { CartComponent } from './home/cart/cart.component';
import { CheckoutComponent } from './home/checkout/checkout.component';
import { MyProfileComponent } from './home/my-profile/my-profile.component';
import { ProfileNavigationComponent } from './home/my-profile/profile-navigation/profile-navigation.component';
import { MyReviewsComponent } from './home/my-profile/my-reviews/my-reviews.component';
import { UserHeaderComponent } from './home/my-profile/user-header/user-header.component';
import { MyOrdersComponent } from './home/my-profile/my-orders/my-orders.component';
import { OwnedBooksComponent } from './home/my-profile/owned-books/owned-books.component';
import { PaymentSuccessComponent } from './home/payment-success/payment-success.component';
import { AuthIntercepterService } from './services/auth-interceptor.service';
import { EditProfileComponent } from './home/my-profile/edit-profile/edit-profile.component';
import { ExchangeComponent } from './home/exchange/exchange.component';
import { ExchangesComponent } from './home/my-profile/exchanges/exchanges.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ToastrModule } from 'ngx-toastr';
import { AboutComponent } from './home/about/about.component';

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
    CartComponent,
    CheckoutComponent,
    MyProfileComponent,
    ProfileNavigationComponent,
    MyReviewsComponent,
    UserHeaderComponent,
    MyOrdersComponent,
    OwnedBooksComponent,
    PaymentSuccessComponent,
    EditProfileComponent,
    ExchangeComponent,
    ExchangesComponent,
    NotFoundComponent,
    AboutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthIntercepterService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
