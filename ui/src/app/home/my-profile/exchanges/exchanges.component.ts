import { Component, OnInit } from '@angular/core';
import { ExchangeRequest } from 'src/app/models/ExchangeRequest.model';
import { AuthService } from 'src/app/services/auth.service';
import { ExchangeService } from 'src/app/services/exchange.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-exchanges',
  templateUrl: './exchanges.component.html',
  styleUrls: ['./exchanges.component.css']
})
export class ExchangesComponent implements OnInit {

  constructor(private exchangeService: ExchangeService, 
    private userService: UserService,
    public auth: AuthService) { }

  
  exchanges: ExchangeRequest[] = [];

  ngOnInit(): void {
    this.getUserExchanges();
  }

  getUserExchanges(){
    this.userService.getUserExchanges(this.auth.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.exchanges = res;
      }
    });
  }

}
