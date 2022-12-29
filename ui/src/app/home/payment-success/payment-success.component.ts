import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.css']
})
export class PaymentSuccessComponent implements OnInit {

  authService: AuthService;

  constructor(authService: AuthService) { 
    this.authService = authService;
  }

  isLoading: boolean = false;

  ngOnInit(): void {
  }

}
