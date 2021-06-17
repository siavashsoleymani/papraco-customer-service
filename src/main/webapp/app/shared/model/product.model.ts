import dayjs from 'dayjs';
import { IFile } from 'app/shared/model/file.model';
import { ProductKind } from 'app/shared/model/enumerations/product-kind.model';

export interface IProduct {
  id?: string;
  name?: string | null;
  productKind?: ProductKind | null;
  remainCount?: number | null;
  reservedCount?: number | null;
  boughtCost?: number | null;
  boughtAt?: string | null;
  factureNumber?: string | null;
  description?: string | null;
  productOrigin?: string | null;
  contractFiels?: IFile[] | null;
}

export const defaultValue: Readonly<IProduct> = {};
