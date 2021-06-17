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
import { getEntity, updateEntity, createEntity, reset } from './facture.reducer';
import { IFacture } from 'app/shared/model/facture.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFactureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactureUpdate = (props: IFactureUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { factureEntity, companies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/facture' + props.location.search);
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
        ...factureEntity,
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
          <h2 id="customerserviceApp.facture.home.createOrEditLabel" data-cy="FactureCreateUpdateHeading">
            Create or edit a Facture
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : factureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="facture-id">ID</Label>
                  <AvInput id="facture-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="facture-title">
                  Title
                </Label>
                <AvField id="facture-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="factureTypeLabel" for="facture-factureType">
                  Facture Type
                </Label>
                <AvInput
                  id="facture-factureType"
                  data-cy="factureType"
                  type="select"
                  className="form-control"
                  name="factureType"
                  value={(!isNew && factureEntity.factureType) || 'OFFICIAL'}
                >
                  <option value="OFFICIAL">OFFICIAL</option>
                  <option value="UNOFFICIAL">UNOFFICIAL</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="agreedLabel">
                  <AvInput id="facture-agreed" data-cy="agreed" type="checkbox" className="form-check-input" name="agreed" />
                  Agreed
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="notificationLabel" for="facture-notification">
                  Notification
                </Label>
                <AvField id="facture-notification" data-cy="notification" type="text" name="notification" />
              </AvGroup>
              <AvGroup check>
                <Label id="checkedoutLabel">
                  <AvInput id="facture-checkedout" data-cy="checkedout" type="checkbox" className="form-check-input" name="checkedout" />
                  Checkedout
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="facture-company">Company</Label>
                <AvInput id="facture-company" data-cy="company" type="select" className="form-control" name="companyId">
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
              <Button tag={Link} id="cancel-save" to="/facture" replace color="info">
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
  factureEntity: storeState.facture.entity,
  loading: storeState.facture.loading,
  updating: storeState.facture.updating,
  updateSuccess: storeState.facture.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(FactureUpdate);
