import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
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
  currentUser: User = new User();   // any user
  currentLoggedInUser: string = ''; // current logged in user
  viewingOtherProfile: boolean = false;

  constructor(private userService: UserService,
    private authService: AuthService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.currentLoggedInUser = this.authService.getCurrentLoggedInUsername();
    
    this.getUserDetails();

    this.userService.userUpdated.subscribe(res => {
      this.getUserDetails();
    });
    
    this.getUserCreditBalance();
  }
  
  getUserDetails(){
    this.route.params.subscribe((param: Params)=>{
      this.userService.getUserByUsername(param['user']).subscribe({
        next: res => {
          this.currentUser = res;
          this.viewingOtherProfile = this.currentUser.username != this.currentLoggedInUser;
        }
      })
    });
  }
  
  getUserCreditBalance(){
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
