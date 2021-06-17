import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FactureItem from './facture-item';
import FactureItemDetail from './facture-item-detail';
import FactureItemUpdate from './facture-item-update';
import FactureItemDeleteDialog from './facture-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FactureItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FactureItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FactureItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={FactureItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FactureItemDeleteDialog} />
  </>
);

export default Routes;
