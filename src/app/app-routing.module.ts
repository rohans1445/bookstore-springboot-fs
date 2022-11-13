import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookListComponent } from './book-list/book-list.component';

const routes: Routes = [
  {path: '', redirectTo: 'books/list', pathMatch: 'full'},
  {path: 'books', redirectTo: 'books/list'},
  {path: 'books', children: [
    {path: 'list', component: BookListComponent},
    {path: ':id', component: BookDetailComponent},
  ]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
