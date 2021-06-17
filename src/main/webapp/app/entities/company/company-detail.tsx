import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyDetail = (props: ICompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">Company</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{companyEntity.email}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{companyEntity.phoneNumber}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{companyEntity.address}</dd>
          <dt>
            <span id="website">Website</span>
          </dt>
          <dd>{companyEntity.website}</dd>
          <dt>
            <span id="photo">Photo</span>
          </dt>
          <dd>
            {companyEntity.photo ? (
              <div>
                {companyEntity.photoContentType ? (
                  <a onClick={openFile(companyEntity.photoContentType, companyEntity.photo)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {companyEntity.photoContentType}, {byteSize(companyEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Tag</dt>
          <dd>
            {companyEntity.tags
              ? companyEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {companyEntity.tags && i === companyEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyEntity: company.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyDetail);
