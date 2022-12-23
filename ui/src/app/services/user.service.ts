import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../models/review.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUserCredit(username: string): Observable<{amount: number}>{
    return this.http.get<{amount: number}>(`${environment.baseUrl}/user/${username}/credits`, {headers: this.authService.getAuthHeader()});
  }

  itemOwnedByUser(username: string, bookId: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}/user/${username}/item-owned/${bookId}`, {headers: this.authService.getAuthHeader()});
  }

  getUserReviews(username: string): Observable<Review[]>{
    return this.http.get<Review[]>(`${environment.baseUrl}/user/${username}/reviews`, {headers: this.authService.getAuthHeader()});
  }
  
}
