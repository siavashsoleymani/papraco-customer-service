import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductUpdate = (props: IProductUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/product' + props.location.search);
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
    values.boughtAt = convertDateTimeToServer(values.boughtAt);

    if (errors.length === 0) {
      const entity = {
        ...productEntity,
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
          <h2 id="customerserviceApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            Create or edit a Product
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="product-id">ID</Label>
                  <AvInput id="product-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="product-name">
                  Name
                </Label>
                <AvField id="product-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="productKindLabel" for="product-productKind">
                  Product Kind
                </Label>
                <AvInput
                  id="product-productKind"
                  data-cy="productKind"
                  type="select"
                  className="form-control"
                  name="productKind"
                  value={(!isNew && productEntity.productKind) || 'SERVICE'}
                >
                  <option value="SERVICE">SERVICE</option>
                  <option value="PRODUCT">PRODUCT</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="remainCountLabel" for="product-remainCount">
                  Remain Count
                </Label>
                <AvField id="product-remainCount" data-cy="remainCount" type="string" className="form-control" name="remainCount" />
              </AvGroup>
              <AvGroup>
                <Label id="reservedCountLabel" for="product-reservedCount">
                  Reserved Count
                </Label>
                <AvField id="product-reservedCount" data-cy="reservedCount" type="string" className="form-control" name="reservedCount" />
              </AvGroup>
              <AvGroup>
                <Label id="boughtCostLabel" for="product-boughtCost">
                  Bought Cost
                </Label>
                <AvField id="product-boughtCost" data-cy="boughtCost" type="text" name="boughtCost" />
              </AvGroup>
              <AvGroup>
                <Label id="boughtAtLabel" for="product-boughtAt">
                  Bought At
                </Label>
                <AvInput
                  id="product-boughtAt"
                  data-cy="boughtAt"
                  type="datetime-local"
                  className="form-control"
                  name="boughtAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.productEntity.boughtAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="factureNumberLabel" for="product-factureNumber">
                  Facture Number
                </Label>
                <AvField id="product-factureNumber" data-cy="factureNumber" type="text" name="factureNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="product-description">
                  Description
                </Label>
                <AvField id="product-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="productOriginLabel" for="product-productOrigin">
                  Product Origin
                </Label>
                <AvField id="product-productOrigin" data-cy="productOrigin" type="text" name="productOrigin" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/product" replace color="info">
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
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductUpdate);
