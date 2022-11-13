import { BookDetail } from "./bookDetail.model";

export interface Book {
    id: number,
    title: string,
    author: string,
    shortDesc: string,
    price: number,
    imgPath: string,
    bookDetail: BookDetail,
    createdAt: Date 
}