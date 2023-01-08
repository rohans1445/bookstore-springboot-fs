import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { UpdateUserParams } from 'src/app/models/UpdateUserParams.model';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  isLoading: boolean = false;
  updateUserParams!: UpdateUserParams;

  constructor(private userService: UserService,
    private auth: AuthService) { }

  ngOnInit(): void {
  }

  onSubmitPersonalDetailsForm(form: NgForm){
    this.updateUserParams = {
      firstName: form.value.firstname,
      lastName: form.value.lastname,
      email: form.value.email,
    }

    this.isLoading = true;
    this.updateUser();
  }

  onSubmitPassChangeForm(form: NgForm){
    this.updateUserParams = {
      password: form.value.password,
    }
    
    this.isLoading = true;
    this.updateUser();
  }
  
  onSubmitUsernameChangeForm(form: NgForm){
    this.updateUserParams = {
      username: form.value.username,
    }
    
    this.isLoading = true;
    this.updateUser();
  }

  onSubmitImgChangeForm(form: NgForm){
    this.updateUserParams = {
      imgUrl: form.value.imgUrl,
    }
    
    this.isLoading = true;
    this.updateUser();
  }
  
  updateUser(){
    this.userService.updateUser(this.auth.getCurrentLoggedInUsername(), this.updateUserParams).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.userService.userUpdated.next(true);
      }
    });
  }
  
}
