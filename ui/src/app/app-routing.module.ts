import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { BookDetailComponent } from './home/books/book-detail/book-detail.component';
import { BookListComponent } from './home/books/book-list/book-list.component';
import { BooksComponent } from './home/books/books.component';
import { CartComponent } from './home/cart/cart.component';
import { CheckoutComponent } from './home/checkout/checkout.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: '', redirectTo: 'books/list', pathMatch: 'full'},
  {path: 'books', redirectTo: 'books/list'},
  {path: '', component: HomeComponent, children: [
    {path: 'books', component: BooksComponent, children: [
      {path: 'list', component: BookListComponent},
      {path: ':id', component: BookDetailComponent},
    ]}, 
    {path: 'cart', component: CartComponent}, 
    {path: 'checkout', component: CheckoutComponent},
  ]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
