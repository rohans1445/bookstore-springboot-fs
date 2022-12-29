import { Book } from "./book.model";

export interface Order {
    id: number;
    total: string;
    paymentType: string;
    status: string;
    receiptUrl: string;
    createdAt: Date;
    orderItems: Book[];
}