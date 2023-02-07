import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { AboutComponent } from './home/about/about.component';
import { BookDetailComponent } from './home/books/book-detail/book-detail.component';
import { BookListComponent } from './home/books/book-list/book-list.component';
import { BooksComponent } from './home/books/books.component';
import { CartComponent } from './home/cart/cart.component';
import { CheckoutComponent } from './home/checkout/checkout.component';
import { ExchangeComponent } from './home/exchange/exchange.component';
import { HomeComponent } from './home/home.component';
import { EditProfileComponent } from './home/my-profile/edit-profile/edit-profile.component';
import { ExchangesComponent } from './home/my-profile/exchanges/exchanges.component';
import { MyOrdersComponent } from './home/my-profile/my-orders/my-orders.component';
import { MyProfileComponent } from './home/my-profile/my-profile.component';
import { MyReviewsComponent } from './home/my-profile/my-reviews/my-reviews.component';
import { OwnedBooksComponent } from './home/my-profile/owned-books/owned-books.component';
import { PaymentSuccessComponent } from './home/payment-success/payment-success.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { AuthGuard } from './services/auth-guard.service';

const routes: Routes = [
  {path: '', redirectTo: 'books/list', pathMatch: 'full'},
  {path: 'books', redirectTo: 'books/list', },
  {path: '', component: HomeComponent, children: [
    {path: 'books', component: BooksComponent, children: [
      {path: 'list', component: BookListComponent, title: 'Spring Bookstore'},
      {path: ':id', component: BookDetailComponent, canActivate: [AuthGuard]},
    ]}, 
    {path: 'cart', component: CartComponent, canActivate: [AuthGuard]}, 
    {path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard]},
    {path: 'user/:user', redirectTo: 'user/:user/my-reviews'},
    {path: 'user/:user', component: MyProfileComponent, canActivateChild: [AuthGuard], children: [
      {path: 'my-reviews', component: MyReviewsComponent},
      {path: 'my-orders', component: MyOrdersComponent},
      {path: 'owned-books', component: OwnedBooksComponent},
      {path: 'edit-profile', component: EditProfileComponent},
      {path: 'exchanges', component: ExchangesComponent},
    ]},
    {path: 'payment/success', component: PaymentSuccessComponent, canActivate: [AuthGuard]},
    {path: 'exchange', component: ExchangeComponent, canActivate: [AuthGuard]},
    {path: 'about', component: AboutComponent},
    {path: 'not-found', component: NotFoundComponent},
  ]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
