import { IStaff } from 'app/shared/model/staff.model';
import { IOffer } from 'app/shared/model/offer.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IFile {
  id?: string;
  path?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  staff?: IStaff | null;
  offer?: IOffer | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IFile> = {};
