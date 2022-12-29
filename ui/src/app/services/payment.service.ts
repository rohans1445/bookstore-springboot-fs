import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PaymentType } from '../models/PaymentType';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient,
    private authService: AuthService) { }

  processPayment(cart: number[], discount: string, paymentType: PaymentType): Observable<{session_url: string, session_id: string}>{
    return this.http.post<{session_url: string, session_id: string}>(`${environment.baseUrl}/process-payment`, {cart, discount, "paymentType": paymentType}, {headers: this.authService.getAuthHeader()});
  }

}
