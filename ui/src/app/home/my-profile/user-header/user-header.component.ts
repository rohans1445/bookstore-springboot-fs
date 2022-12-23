import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css']
})
export class UserHeaderComponent implements OnInit {

  userCreditBal: number = 0;
  currentUser: User = new User();

  constructor(private userService: UserService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.currentUser = this.authService.currentUser;
    this.userService.getUserCredit(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.userCreditBal = res.amount;
      },
      error: error => {
        this.userCreditBal = -1;
      }
    });
  }

}
