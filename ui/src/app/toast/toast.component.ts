import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {

  constructor() { }

  @Input()
  type: string = 'danger';

  @Input()
  message: string = '';

  ngOnInit(): void {
  }

}
