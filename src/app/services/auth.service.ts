import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../user.model';
import jwtDecode from 'jwt-decode';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  userHasLoggedOut = new BehaviorSubject<boolean>(false);

  login(usernamePassword: object): Observable<any>{
    return this.http.post<any>(`${environment.baseUrl}/auth/login`, usernamePassword);
  }

  getCurrentUserDetails(): Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/api/user/me`, {headers: this.getAuthHeader()});
  }

  isLoggedIn(){
    return moment().isBefore(this.getExpiration());
  }

  getExpiration(){
    const bearerToken = localStorage.getItem('token');
    const decodedJWT: {iat: number, sub: string, exp: number } = jwtDecode(!!bearerToken ? bearerToken : '');
    return moment.unix(decodedJWT.exp);
  }

  logout() {
    localStorage.removeItem('token');
  }

  getAuthHeader() {
    const bearerToken = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${bearerToken}`
    });
  }


}
