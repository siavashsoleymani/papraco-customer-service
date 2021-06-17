import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDetail = (props: IProductDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="productKind">Product Kind</span>
          </dt>
          <dd>{productEntity.productKind}</dd>
          <dt>
            <span id="remainCount">Remain Count</span>
          </dt>
          <dd>{productEntity.remainCount}</dd>
          <dt>
            <span id="reservedCount">Reserved Count</span>
          </dt>
          <dd>{productEntity.reservedCount}</dd>
          <dt>
            <span id="boughtCost">Bought Cost</span>
          </dt>
          <dd>{productEntity.boughtCost}</dd>
          <dt>
            <span id="boughtAt">Bought At</span>
          </dt>
          <dd>{productEntity.boughtAt ? <TextFormat value={productEntity.boughtAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="factureNumber">Facture Number</span>
          </dt>
          <dd>{productEntity.factureNumber}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="productOrigin">Product Origin</span>
          </dt>
          <dd>{productEntity.productOrigin}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetail);
