import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Book } from 'src/app/models/book.model';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {

  bookForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  isSaving: boolean = false;
  editBookId: number = 0;
  
  constructor(private bookService: BookService,
    private currentRoute: ActivatedRoute) { }
  
  @Input()
  editMode: boolean = false;

  @Input()
  book: Book = new Book();

  @Output()
  modalClose = new EventEmitter<void>();

  ngOnInit(): void {
    if(this.editMode){
      this.bookForm = new FormGroup({
        title: new FormControl(this.book.title, Validators.required),
        author: new FormControl(this.book.author, Validators.required),
        price: new FormControl(this.book.price, Validators.required),
        shortDesc: new FormControl(this.book.shortDesc, Validators.required),
        imgPath: new FormControl(this.book.imgPath, Validators.required),
        bookDetail: new FormGroup({
          isbn: new FormControl(this.book.bookDetail?.isbn, Validators.required),
          language: new FormControl(this.book.bookDetail?.language, Validators.required),
          publisher: new FormControl(this.book.bookDetail?.publisher, Validators.required),
        })
      })
    } else {
      this.bookForm = new FormGroup({
        title: new FormControl('', Validators.required),
        author: new FormControl('', Validators.required),
        price: new FormControl('', Validators.required),
        shortDesc: new FormControl('', Validators.required),
        imgPath: new FormControl('', Validators.required),
        bookDetail: new FormGroup({
          isbn: new FormControl('', Validators.required),
          language: new FormControl('', Validators.required),
          publisher: new FormControl('', Validators.required),
        })
      });
    }
  }

  onCloseModal(){
    this.modalClose.emit();
  }

  onSubmit(){
    this.isSaving = true;
    if(!this.editMode){
      this.bookService.saveBook(this.bookForm.value).subscribe({
        next: res => {
          this.isSaving = false;
          this.onCloseModal();
        },
        error: error => {
          console.log(error.message);
        }
      })
    } else {
      this.currentRoute.params.subscribe((params)=>{
        this.editBookId = +params['id'];
      })

      let bookToSave: Book = this.bookForm.value;
      bookToSave.id = this.editBookId;
      
      this.bookService.updateBook(bookToSave).subscribe({
        next: res => {
          this.isSaving = false;
          this.onCloseModal();
        },
        error: error => {
          console.log(error.message);
        }
      })
    }
  }

  fetchRandomBook(){
    this.isLoading = true;
    this.bookService.getRandomBook().subscribe({
      next: res => {
        this.isLoading = false;
        this.bookForm.setValue({
          title: res.title,
          author: res.author,
          price: res.price,
          shortDesc: res.shortDesc,
          imgPath: res.imgPath,
          bookDetail: res.bookDetail,
        })
      },
      error: error => {
        this.isLoading = false;
        console.log(error.message);
      }
    })
  }

  onReset(){
    this.bookForm.reset();
  }

}