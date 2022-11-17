import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user.model';
import jwtDecode from 'jwt-decode';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  currentUser: User = new User();

  userHasLoggedOut = new BehaviorSubject<boolean>(false);

  login(usernamePassword: object): Observable<any>{
    return this.http.post<any>(`${environment.baseUrl}/auth/login`, usernamePassword);
  }

  fetchCurrentUserDetails(): Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/user/me`, {headers: this.getAuthHeader()});
  }

  isLoggedIn(): boolean{
    return !this.isTokenExpired();
  }

  setLoggedInUser(){
    if(this.isLoggedIn()){
      this.fetchCurrentUserDetails().subscribe(res=>{
        this.currentUser = res;
      })
    }
  }

  getExpiration(){
    try {
      const bearerToken = localStorage.getItem('token');
      const decodedJWT: {iat: number, sub: string, exp: number } = jwtDecode(bearerToken!);
      return moment.unix(decodedJWT.exp);
    } catch(error) {
      console.log('Error decoding token.');
      return;
    }
  }
  
  isTokenExpired(){
    const bearerToken = localStorage.getItem('token');
    if(bearerToken === null){
      return true;
    }

    const decodedJWT: {iat: number, sub: string, exp: number } = jwtDecode(bearerToken);
    return moment().isAfter(moment.unix(decodedJWT.exp)); 
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
