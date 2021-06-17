import { ICompany } from 'app/shared/model/company.model';

export interface ITag {
  id?: string;
  name?: string | null;
  companies?: ICompany[] | null;
}

export const defaultValue: Readonly<ITag> = {};
