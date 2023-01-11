import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../models/book.model';
import { BookList } from '../models/BookList.model';
import { PagedResponse } from '../models/PagedResponse.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  private baseUrl: string = environment.baseUrl;
  
  constructor(private http: HttpClient) {}
    
  getBooksPaged(page: number, size: number): Observable<PagedResponse<BookList>> {
    return this.http.get<PagedResponse<BookList>>(`${this.baseUrl}/books`, {params: {page: page, size: size}});
  }

  getAllBooks(): Observable<BookList[]> {
    return this.http.get<BookList[]>(`${this.baseUrl}/books/all`);
  }
  
  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/books/${id}`);
  }

  getRandomBook(): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/rnd-book`);
  }
  
  saveBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.baseUrl}/books`, book);
  }
  
  updateBook(book: Book) {
    return this.http.put<Book>(`${this.baseUrl}/books`, book);
  }

  searchBook(searchString: string): Observable<BookList[]>{
    return this.http.get<BookList[]>(`${environment.baseUrl}/books/search?title=${searchString}`)
  }
  
}
