import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContractKind from './contract-kind';
import ContractKindDetail from './contract-kind-detail';
import ContractKindUpdate from './contract-kind-update';
import ContractKindDeleteDialog from './contract-kind-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContractKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContractKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContractKindDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContractKind} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContractKindDeleteDialog} />
  </>
);

export default Routes;
