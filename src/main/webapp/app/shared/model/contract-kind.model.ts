import { IContract } from 'app/shared/model/contract.model';

export interface IContractKind {
  id?: string;
  name?: string | null;
  contract?: IContract | null;
}

export const defaultValue: Readonly<IContractKind> = {};
