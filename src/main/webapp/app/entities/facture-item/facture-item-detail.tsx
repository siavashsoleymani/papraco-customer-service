import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './facture-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFactureItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactureItemDetail = (props: IFactureItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { factureItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="factureItemDetailsHeading">FactureItem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{factureItemEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{factureItemEntity.description}</dd>
          <dt>
            <span id="count">Count</span>
          </dt>
          <dd>{factureItemEntity.count}</dd>
          <dt>
            <span id="measureType">Measure Type</span>
          </dt>
          <dd>{factureItemEntity.measureType}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{factureItemEntity.amount}</dd>
          <dt>
            <span id="totalAmount">Total Amount</span>
          </dt>
          <dd>{factureItemEntity.totalAmount}</dd>
          <dt>
            <span id="discount">Discount</span>
          </dt>
          <dd>{factureItemEntity.discount}</dd>
          <dt>
            <span id="tax">Tax</span>
          </dt>
          <dd>{factureItemEntity.tax}</dd>
        </dl>
        <Button tag={Link} to="/facture-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/facture-item/${factureItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ factureItem }: IRootState) => ({
  factureItemEntity: factureItem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FactureItemDetail);
