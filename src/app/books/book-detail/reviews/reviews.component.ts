import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Review } from 'src/app/review.model';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {

  isLoading: boolean = true;
  isError: boolean = false;
  reviews: Review[] = [];

  constructor(private reviewService: ReviewService, 
    private currentRoute: ActivatedRoute) { }

  ngOnInit(): void {
    let currentBookId = 0;
    this.isLoading = true;

    this.currentRoute.params.subscribe({
      next: (param: Params) => {currentBookId = param['id']}
    });

    this.reviewService.getReviewByBookId(currentBookId).subscribe({
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

}
