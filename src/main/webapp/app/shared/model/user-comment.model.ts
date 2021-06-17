import { ICompany } from 'app/shared/model/company.model';

export interface IUserComment {
  id?: string;
  comment?: string | null;
  score?: number | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IUserComment> = {};
