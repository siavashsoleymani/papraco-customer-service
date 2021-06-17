import { IStaff } from 'app/shared/model/staff.model';
import { IOffer } from 'app/shared/model/offer.model';
import { IContract } from 'app/shared/model/contract.model';
import { IFacture } from 'app/shared/model/facture.model';
import { IUserComment } from 'app/shared/model/user-comment.model';
import { ITag } from 'app/shared/model/tag.model';

export interface ICompany {
  id?: string;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
  website?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  staff?: IStaff[] | null;
  offers?: IOffer[] | null;
  contracts?: IContract[] | null;
  factures?: IFacture[] | null;
  userComments?: IUserComment[] | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
