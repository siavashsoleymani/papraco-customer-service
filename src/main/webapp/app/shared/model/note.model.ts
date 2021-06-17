import { IStaff } from 'app/shared/model/staff.model';

export interface INote {
  id?: string;
  note?: string | null;
  staff?: IStaff | null;
}

export const defaultValue: Readonly<INote> = {};
