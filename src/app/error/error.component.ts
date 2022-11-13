import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  template: `
  <div class="alert alert-danger" role="alert">
    <i class="bi bi-info-circle me-2"></i> <span>Error fetching content.</span>
  </div>
  `,
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
