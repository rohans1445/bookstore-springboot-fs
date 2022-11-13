import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <section th:fragment="footer" class="mt-auto">
    <footer class="text-center text-white bg-dark border-custom-orange-top">
      <div class="text-center p-3">
        <div class="text-muted">© 2022 bookstore-springboot-NG</div>
      </div>
    </footer>
    </section>
  `,
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
