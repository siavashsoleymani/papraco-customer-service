import { IFile } from 'app/shared/model/file.model';
import { ICompany } from 'app/shared/model/company.model';
import { OfferType } from 'app/shared/model/enumerations/offer-type.model';

export interface IOffer {
  id?: string;
  title?: string | null;
  body?: string | null;
  offerType?: OfferType | null;
  notifInterval?: number | null;
  photos?: IFile[] | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IOffer> = {};
