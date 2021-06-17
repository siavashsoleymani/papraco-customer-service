import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './offer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOfferDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OfferDetail = (props: IOfferDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { offerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="offerDetailsHeading">Offer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{offerEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{offerEntity.title}</dd>
          <dt>
            <span id="body">Body</span>
          </dt>
          <dd>{offerEntity.body}</dd>
          <dt>
            <span id="offerType">Offer Type</span>
          </dt>
          <dd>{offerEntity.offerType}</dd>
          <dt>
            <span id="notifInterval">Notif Interval</span>
          </dt>
          <dd>{offerEntity.notifInterval}</dd>
          <dt>Company</dt>
          <dd>{offerEntity.company ? offerEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/offer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/offer/${offerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ offer }: IRootState) => ({
  offerEntity: offer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfferDetail);
