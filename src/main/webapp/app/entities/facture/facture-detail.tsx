import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './facture.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFactureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactureDetail = (props: IFactureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { factureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="factureDetailsHeading">Facture</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{factureEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{factureEntity.title}</dd>
          <dt>
            <span id="factureType">Facture Type</span>
          </dt>
          <dd>{factureEntity.factureType}</dd>
          <dt>
            <span id="agreed">Agreed</span>
          </dt>
          <dd>{factureEntity.agreed ? 'true' : 'false'}</dd>
          <dt>
            <span id="notification">Notification</span>
          </dt>
          <dd>{factureEntity.notification}</dd>
          <dt>
            <span id="checkedout">Checkedout</span>
          </dt>
          <dd>{factureEntity.checkedout ? 'true' : 'false'}</dd>
          <dt>Company</dt>
          <dd>{factureEntity.company ? factureEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/facture" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/facture/${factureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ facture }: IRootState) => ({
  factureEntity: facture.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FactureDetail);
