import { ICompany } from 'app/shared/model/company.model';
import { FactureType } from 'app/shared/model/enumerations/facture-type.model';

export interface IFacture {
  id?: string;
  title?: string | null;
  factureType?: FactureType | null;
  agreed?: boolean | null;
  notification?: string | null;
  checkedout?: boolean | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IFacture> = {
  agreed: false,
  checkedout: false,
};
