import { MeasureType } from 'app/shared/model/enumerations/measure-type.model';

export interface IFactureItem {
  id?: string;
  description?: string | null;
  count?: number | null;
  measureType?: MeasureType | null;
  amount?: number | null;
  totalAmount?: number | null;
  discount?: number | null;
  tax?: number | null;
}

export const defaultValue: Readonly<IFactureItem> = {};
