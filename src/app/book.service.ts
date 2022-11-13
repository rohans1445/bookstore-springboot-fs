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
  private token: string = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2ODM1NzcxNywiZXhwIjoxNjY4MzY0OTE3fQ.prW36r5cqWSiyPi-qNhzjIejVk2y9fFD81Td6HysplB3BFUf4sHg_2xrz9aiYw01xsqbFFD1xpTmPlCNy_a9WQ'
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
