import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../models/review.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }


  getReviewByBookId(id: number): Observable<Review[]>{
    return this.http.get<Review[]>(`${environment.baseUrl}/books/${id}/reviews`);
  }

  addReview(bookId: number, newReview: {title: string, content: string, rating: number}){
    return this.http.post<Review>(`${environment.baseUrl}/books/${bookId}/reviews`, newReview);
  }

  deleteReview(reviewId: number, bookId: number): Observable<any>{
    return this.http.delete(`${environment.baseUrl}/books/${bookId}/reviews/${reviewId}`)
  }

}
