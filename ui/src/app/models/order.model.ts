import { Book } from "./book.model";

export interface Order {
    id: number;
    total: string;
    paymentType: string;
    createdAt: Date;
    orderItems: Book[];
}