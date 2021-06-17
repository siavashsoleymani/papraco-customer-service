import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import userComment, {
  UserCommentState
} from 'app/entities/user-comment/user-comment.reducer';
// prettier-ignore
import tag, {
  TagState
} from 'app/entities/tag/tag.reducer';
// prettier-ignore
import staff, {
  StaffState
} from 'app/entities/staff/staff.reducer';
// prettier-ignore
import file, {
  FileState
} from 'app/entities/file/file.reducer';
// prettier-ignore
import note, {
  NoteState
} from 'app/entities/note/note.reducer';
// prettier-ignore
import offer, {
  OfferState
} from 'app/entities/offer/offer.reducer';
// prettier-ignore
import contract, {
  ContractState
} from 'app/entities/contract/contract.reducer';
// prettier-ignore
import contractKind, {
  ContractKindState
} from 'app/entities/contract-kind/contract-kind.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import facture, {
  FactureState
} from 'app/entities/facture/facture.reducer';
// prettier-ignore
import factureItem, {
  FactureItemState
} from 'app/entities/facture-item/facture-item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly company: CompanyState;
  readonly userComment: UserCommentState;
  readonly tag: TagState;
  readonly staff: StaffState;
  readonly file: FileState;
  readonly note: NoteState;
  readonly offer: OfferState;
  readonly contract: ContractState;
  readonly contractKind: ContractKindState;
  readonly product: ProductState;
  readonly facture: FactureState;
  readonly factureItem: FactureItemState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  company,
  userComment,
  tag,
  staff,
  file,
  note,
  offer,
  contract,
  contractKind,
  product,
  facture,
  factureItem,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
