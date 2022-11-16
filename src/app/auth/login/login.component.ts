import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmitLogin(){
    const formValue = this.loginForm.value;
    this.authService.login({
      username: formValue.username,
      password: formValue.password,
    }).subscribe({
      next: res => {
        console.log(res);
        localStorage.setItem('token', res.token);
        this.router.navigate(['/books/list']);
      }
    });
  }

}
