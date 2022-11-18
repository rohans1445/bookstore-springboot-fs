import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../models/book.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  private baseUrl: string = environment.baseUrl;
  
  constructor(private http: HttpClient,
    private authService: AuthService) {}
    
  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.baseUrl}/books`, {headers: this.authService.getAuthHeader()});
  }
  
  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/books/${id}`, {headers: this.authService.getAuthHeader()});
  }

  getRandomBook(): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/rnd-book`, {headers: this.authService.getAuthHeader()});
  }
  
  saveBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.baseUrl}/books`, book, {headers: this.authService.getAuthHeader()});
  }
  
  updateBook(book: Book) {
    return this.http.put<Book>(`${this.baseUrl}/books`, book, {headers: this.authService.getAuthHeader()});
  }
  
}
