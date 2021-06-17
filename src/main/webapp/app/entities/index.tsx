import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Company from './company';
import UserComment from './user-comment';
import Tag from './tag';
import Staff from './staff';
import File from './file';
import Note from './note';
import Offer from './offer';
import Contract from './contract';
import ContractKind from './contract-kind';
import Product from './product';
import Facture from './facture';
import FactureItem from './facture-item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}user-comment`} component={UserComment} />
      <ErrorBoundaryRoute path={`${match.url}tag`} component={Tag} />
      <ErrorBoundaryRoute path={`${match.url}staff`} component={Staff} />
      <ErrorBoundaryRoute path={`${match.url}file`} component={File} />
      <ErrorBoundaryRoute path={`${match.url}note`} component={Note} />
      <ErrorBoundaryRoute path={`${match.url}offer`} component={Offer} />
      <ErrorBoundaryRoute path={`${match.url}contract`} component={Contract} />
      <ErrorBoundaryRoute path={`${match.url}contract-kind`} component={ContractKind} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}facture`} component={Facture} />
      <ErrorBoundaryRoute path={`${match.url}facture-item`} component={FactureItem} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
