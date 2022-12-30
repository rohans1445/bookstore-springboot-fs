import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../models/book.model';
import { Order } from '../models/order.model';
import { Review } from '../models/review.model';
import { User } from '../models/user.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUserByUsername(username: string): Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/user/${username}`, {headers: this.authService.getAuthHeader()});
  }

  getUserCredit(username: string): Observable<{amount: number}>{
    return this.http.get<{amount: number}>(`${environment.baseUrl}/user/${username}/credits`, {headers: this.authService.getAuthHeader()});
  }

  itemOwnedByUser(username: string, bookId: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}/user/${username}/item-owned/${bookId}`, {headers: this.authService.getAuthHeader()});
  }

  getUserReviews(username: string): Observable<Review[]>{
    return this.http.get<Review[]>(`${environment.baseUrl}/user/${username}/reviews`, {headers: this.authService.getAuthHeader()});
  }
 
  getUsersOrders(username: string): Observable<Order[]>{
    return this.http.get<Order[]>(`${environment.baseUrl}/user/${username}/orders`, {headers: this.authService.getAuthHeader()});
  }
  
  getUsersBooks(username: string): Observable<Book[]>{
    return this.http.get<Book[]>(`${environment.baseUrl}/user/${username}/owned-books`, {headers: this.authService.getAuthHeader()});
  }

  getUserCartCount(username: string): Observable<{cart_items: number}>{
    return this.http.get<{cart_items:number}>(`${environment.baseUrl}/user/${username}/cart-count`, {headers: this.authService.getAuthHeader()});
  }
}
