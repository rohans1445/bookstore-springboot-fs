import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loggedIn: boolean = false;
  isAdmin: boolean = false;

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
  }

  onLogout(): void {
    this.authService.logout();
    this.authService.userHasLoggedOut.next(true);
    this.router.navigate(['/login']);
  }

}
