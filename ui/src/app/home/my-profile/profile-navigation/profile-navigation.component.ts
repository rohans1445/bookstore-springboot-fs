import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './profile-navigation.component.html',
  styleUrls: ['./profile-navigation.component.css']
})
export class ProfileNavigationComponent implements OnInit {

  currentUsername: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.currentUsername = this.authService.getCurrentLoggedInUsername();
  }

}
