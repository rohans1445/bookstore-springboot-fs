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
  private token: string = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2ODM0Mzk2NSwiZXhwIjoxNjY4MzUxMTY1fQ.CCvPgWSyrNZrC1nW_-6XP-974iWXVMLkE4xmtde4sD4MORapVgZnXKFiLZgjxCmW6ho1mjF2b9H4Iif5Gjcm9Q';

  constructor(private http: HttpClient) {}

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.baseUrl}/books`, {headers: new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    })});
  }

}
