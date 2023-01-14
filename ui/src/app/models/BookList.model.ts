import { BookDetail } from "./bookDetail.model";

export interface BookList {
    id: number;
    title: string;
    author: string;
    shortDesc: string;
    price: number;
    imgPath: string;
    bookDetail: BookDetail;
    createdAt: Date;
    timesPurchased: number;
    avgReviews: number;
    reviewCount: number;
    productPurchased: boolean;
}