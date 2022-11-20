import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../models/book.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl: string = environment.baseUrl;
  
  constructor(private http: HttpClient,
    private authService: AuthService) {}

  getUserCart(): Observable<Book[]>{
    return this.http.get<Book[]>(`${this.baseUrl}/cart`, {headers: this.authService.getAuthHeader()});
  }
    
}
