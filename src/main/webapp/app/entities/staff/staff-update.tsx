import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './staff.reducer';
import { IStaff } from 'app/shared/model/staff.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStaffUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StaffUpdate = (props: IStaffUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { staffEntity, companies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/staff' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...staffEntity,
        ...values,
        company: companies.find(it => it.id.toString() === values.companyId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="customerserviceApp.staff.home.createOrEditLabel" data-cy="StaffCreateUpdateHeading">
            Create or edit a Staff
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : staffEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="staff-id">ID</Label>
                  <AvInput id="staff-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="staff-name">
                  Name
                </Label>
                <AvField id="staff-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="familyLabel" for="staff-family">
                  Family
                </Label>
                <AvField id="staff-family" data-cy="family" type="text" name="family" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="staff-phoneNumber">
                  Phone Number
                </Label>
                <AvField id="staff-phoneNumber" data-cy="phoneNumber" type="text" name="phoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="staff-email">
                  Email
                </Label>
                <AvField id="staff-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="desciptionLabel" for="staff-desciption">
                  Desciption
                </Label>
                <AvField id="staff-desciption" data-cy="desciption" type="text" name="desciption" />
              </AvGroup>
              <AvGroup>
                <Label for="staff-company">Company</Label>
                <AvInput id="staff-company" data-cy="company" type="select" className="form-control" name="companyId">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/staff" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  companies: storeState.company.entities,
  staffEntity: storeState.staff.entity,
  loading: storeState.staff.loading,
  updating: storeState.staff.updating,
  updateSuccess: storeState.staff.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StaffUpdate);
