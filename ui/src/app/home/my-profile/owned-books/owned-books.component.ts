import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-owned-books',
  templateUrl: './owned-books.component.html',
  styleUrls: ['./owned-books.component.css']
})
export class OwnedBooksComponent implements OnInit {

  ownedBooks: Book[] = [];

  constructor(private authService: AuthService,
    private userService: UserService) { }

    isLoading = false;

  ngOnInit(): void {
    this.isLoading = true;
    this.userService.getUsersBooks(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.isLoading = false;
        this.ownedBooks = res;
      }
    });
  }

}
