import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  template: `
  <div class="alert alert-danger col-md-4" role="alert">
    <i class="bi bi-info-circle me-2"></i> <span>{{message === '' || message === null ? 'Error fetching content.' : message}}</span>
  </div>
  `,
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  constructor() { }

  message: string = '';

  ngOnInit(): void {
  }

}
