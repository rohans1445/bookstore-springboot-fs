import { ExchangeStatus } from "./ExchangeStatus.enum";

export interface ExchangeRequest {
     id: number;
     exchangeOpener: String;
     exchangeCloser: String;
     openerOwnedBookId: number;
     openerOwnedBookTitle: String;
     openerExchangeBookId: number;
     openerExchangeBookTitle: String;
     exchangeStatus: ExchangeStatus;
     createdAt: Date;
     closedAt: Date;
     canExchange: boolean;
}