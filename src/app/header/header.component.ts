import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { User } from '../user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loggedIn: boolean = false;
  isAdmin: boolean = false;
  currentUser!: User;

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
    this.authService.setLoggedInUser();
  }
  
  onLogout(): void {
    this.authService.logout();
    this.authService.userHasLoggedOut.next(true);
    this.router.navigate(['/login']);
  }

}
