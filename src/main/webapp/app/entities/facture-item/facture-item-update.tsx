import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './facture-item.reducer';
import { IFactureItem } from 'app/shared/model/facture-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFactureItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactureItemUpdate = (props: IFactureItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { factureItemEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/facture-item' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...factureItemEntity,
        ...values,
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
          <h2 id="customerserviceApp.factureItem.home.createOrEditLabel" data-cy="FactureItemCreateUpdateHeading">
            Create or edit a FactureItem
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : factureItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="facture-item-id">ID</Label>
                  <AvInput id="facture-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="facture-item-description">
                  Description
                </Label>
                <AvField id="facture-item-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="countLabel" for="facture-item-count">
                  Count
                </Label>
                <AvField id="facture-item-count" data-cy="count" type="string" className="form-control" name="count" />
              </AvGroup>
              <AvGroup>
                <Label id="measureTypeLabel" for="facture-item-measureType">
                  Measure Type
                </Label>
                <AvInput
                  id="facture-item-measureType"
                  data-cy="measureType"
                  type="select"
                  className="form-control"
                  name="measureType"
                  value={(!isNew && factureItemEntity.measureType) || 'NUMBER'}
                >
                  <option value="NUMBER">NUMBER</option>
                  <option value="KILO">KILO</option>
                  <option value="MONTH">MONTH</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="facture-item-amount">
                  Amount
                </Label>
                <AvField id="facture-item-amount" data-cy="amount" type="text" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label id="totalAmountLabel" for="facture-item-totalAmount">
                  Total Amount
                </Label>
                <AvField id="facture-item-totalAmount" data-cy="totalAmount" type="text" name="totalAmount" />
              </AvGroup>
              <AvGroup>
                <Label id="discountLabel" for="facture-item-discount">
                  Discount
                </Label>
                <AvField id="facture-item-discount" data-cy="discount" type="text" name="discount" />
              </AvGroup>
              <AvGroup>
                <Label id="taxLabel" for="facture-item-tax">
                  Tax
                </Label>
                <AvField id="facture-item-tax" data-cy="tax" type="text" name="tax" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/facture-item" replace color="info">
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
  factureItemEntity: storeState.factureItem.entity,
  loading: storeState.factureItem.loading,
  updating: storeState.factureItem.updating,
  updateSuccess: storeState.factureItem.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FactureItemUpdate);
