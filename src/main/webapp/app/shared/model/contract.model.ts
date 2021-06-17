import { ICompany } from 'app/shared/model/company.model';
import { IContractKind } from 'app/shared/model/contract-kind.model';

export interface IContract {
  id?: string;
  body?: string | null;
  agreed?: boolean | null;
  company?: ICompany | null;
  contractKinds?: IContractKind[] | null;
}

export const defaultValue: Readonly<IContract> = {
  agreed: false,
};
