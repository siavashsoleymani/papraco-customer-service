import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './staff.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStaffDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StaffDetail = (props: IStaffDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { staffEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="staffDetailsHeading">Staff</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{staffEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{staffEntity.name}</dd>
          <dt>
            <span id="family">Family</span>
          </dt>
          <dd>{staffEntity.family}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{staffEntity.phoneNumber}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{staffEntity.email}</dd>
          <dt>
            <span id="desciption">Desciption</span>
          </dt>
          <dd>{staffEntity.desciption}</dd>
          <dt>Company</dt>
          <dd>{staffEntity.company ? staffEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/staff" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/staff/${staffEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ staff }: IRootState) => ({
  staffEntity: staff.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StaffDetail);
