import dayjs from 'dayjs';

export interface IOrders {
  id?: number;
  customerId?: string;
  product?: string;
  amount?: number;
  createdAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IOrders> = {};
