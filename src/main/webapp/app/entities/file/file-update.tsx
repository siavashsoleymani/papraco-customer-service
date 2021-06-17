import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStaff } from 'app/shared/model/staff.model';
import { getEntities as getStaff } from 'app/entities/staff/staff.reducer';
import { IOffer } from 'app/shared/model/offer.model';
import { getEntities as getOffers } from 'app/entities/offer/offer.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileUpdate = (props: IFileUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { fileEntity, staff, offers, products, loading, updating } = props;

  const { file, fileContentType } = fileEntity;

  const handleClose = () => {
    props.history.push('/file' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStaff();
    props.getOffers();
    props.getProducts();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...fileEntity,
        ...values,
        staff: staff.find(it => it.id.toString() === values.staffId.toString()),
        offer: offers.find(it => it.id.toString() === values.offerId.toString()),
        product: products.find(it => it.id.toString() === values.productId.toString()),
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
          <h2 id="customerserviceApp.file.home.createOrEditLabel" data-cy="FileCreateUpdateHeading">
            Create or edit a File
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fileEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="file-id">ID</Label>
                  <AvInput id="file-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="pathLabel" for="file-path">
                  Path
                </Label>
                <AvField id="file-path" data-cy="path" type="text" name="path" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="fileLabel" for="file">
                    File
                  </Label>
                  <br />
                  {file ? (
                    <div>
                      {fileContentType ? <a onClick={openFile(fileContentType, file)}>Open</a> : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {fileContentType}, {byteSize(file)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('file')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_file" data-cy="file" type="file" onChange={onBlobChange(false, 'file')} />
                  <AvInput type="hidden" name="file" value={file} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label for="file-staff">Staff</Label>
                <AvInput id="file-staff" data-cy="staff" type="select" className="form-control" name="staffId">
                  <option value="" key="0" />
                  {staff
                    ? staff.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="file-offer">Offer</Label>
                <AvInput id="file-offer" data-cy="offer" type="select" className="form-control" name="offerId">
                  <option value="" key="0" />
                  {offers
                    ? offers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="file-product">Product</Label>
                <AvInput id="file-product" data-cy="product" type="select" className="form-control" name="productId">
                  <option value="" key="0" />
                  {products
                    ? products.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/file" replace color="info">
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
  staff: storeState.staff.entities,
  offers: storeState.offer.entities,
  products: storeState.product.entities,
  fileEntity: storeState.file.entity,
  loading: storeState.file.loading,
  updating: storeState.file.updating,
  updateSuccess: storeState.file.updateSuccess,
});

const mapDispatchToProps = {
  getStaff,
  getOffers,
  getProducts,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileUpdate);
