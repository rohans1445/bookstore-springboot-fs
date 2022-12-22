import { animate, state, style, transition, trigger } from '@angular/animations';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { Review } from 'src/app/models/review.model';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {

  isLoading: boolean = true;
  isError: boolean = false;
  isErrorSavingReview: boolean = false;
  isAddingReview: boolean = false;
  isSavingReview: boolean = false;
  showToast: boolean = false;
  toastType: string = '';
  reviewForm: FormGroup = new FormGroup({});
  reviews: Review[] = [];
  currentBookId: number = 0;
  errorCode: number = 0;

  constructor(private reviewService: ReviewService, 
    private currentRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.getAllReviewsForBook();
    
    this.reviewForm = new FormGroup({
      title: new FormControl('', Validators.required),
      content: new FormControl('', Validators.required),
      rating: new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]),
    })
  }

  getAllReviewsForBook(){
    this.isLoading = true;

    this.currentRoute.params.subscribe({
      next: (param: Params) => {this.currentBookId = param['id']}
    });

    this.reviewService.getReviewByBookId(this.currentBookId).subscribe({
      next: res => {
        this.reviews = res;
        this.isLoading = false;
        console.log(res);
      },
      error: error => {
        this.isError = true;
      }
    })
  }

  onReviewSubmit(){
    this.isSavingReview = true;
    this.reviewService.addReview(this.currentBookId, this.reviewForm.value).subscribe({
      next: res => {
        this.isSavingReview = false;
        this.isAddingReview = false;
        this.ngOnInit();
      },
      error: (error: HttpErrorResponse) => {
        this.isSavingReview = false;
        this.isErrorSavingReview = true;
        this.errorCode = error.status;
        console.log(error.message);
      }
    });
  }

  onCloseReviewModal(){
    this.reviewForm.reset();
    this.isAddingReview = false;
    this.isErrorSavingReview = false;
  }

}
