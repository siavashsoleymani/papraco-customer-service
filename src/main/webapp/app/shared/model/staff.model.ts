import { INote } from 'app/shared/model/note.model';
import { IFile } from 'app/shared/model/file.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IStaff {
  id?: string;
  name?: string | null;
  family?: string | null;
  phoneNumber?: string | null;
  email?: string | null;
  desciption?: string | null;
  notes?: INote[] | null;
  profilePhotos?: IFile[] | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IStaff> = {};
