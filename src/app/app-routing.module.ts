import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookDetailComponent } from './books/book-detail/book-detail.component';
import { BookListComponent } from './books/book-list/book-list.component';
import { BooksComponent } from './books/books.component';

const routes: Routes = [
  {path: '', redirectTo: 'books/list', pathMatch: 'full'},
  {path: 'books', component: BooksComponent, children: [
    {path: 'list', component: BookListComponent},
    {path: ':id', component: BookDetailComponent},
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
