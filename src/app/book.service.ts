import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from './book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl: string = environment.baseUrl;
  private token: string | null = localStorage.getItem('token');
  private authHeader: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${this.token}`
  })

  constructor(private http: HttpClient) {}

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.baseUrl}/books`, {headers: this.authHeader});
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/books/${id}`, {headers: this.authHeader});
  }

}
