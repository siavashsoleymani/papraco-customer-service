import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './offer.reducer';
import { IOffer } from 'app/shared/model/offer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOfferUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OfferUpdate = (props: IOfferUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { offerEntity, companies, loading, updating } = props;

  const { body } = offerEntity;

  const handleClose = () => {
    props.history.push('/offer' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
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
        ...offerEntity,
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
          <h2 id="customerserviceApp.offer.home.createOrEditLabel" data-cy="OfferCreateUpdateHeading">
            Create or edit a Offer
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : offerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="offer-id">ID</Label>
                  <AvInput id="offer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="offer-title">
                  Title
                </Label>
                <AvField id="offer-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="bodyLabel" for="offer-body">
                  Body
                </Label>
                <AvInput id="offer-body" data-cy="body" type="textarea" name="body" />
              </AvGroup>
              <AvGroup>
                <Label id="offerTypeLabel" for="offer-offerType">
                  Offer Type
                </Label>
                <AvInput
                  id="offer-offerType"
                  data-cy="offerType"
                  type="select"
                  className="form-control"
                  name="offerType"
                  value={(!isNew && offerEntity.offerType) || 'ADVICE'}
                >
                  <option value="ADVICE">ADVICE</option>
                  <option value="OFFER">OFFER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="notifIntervalLabel" for="offer-notifInterval">
                  Notif Interval
                </Label>
                <AvField id="offer-notifInterval" data-cy="notifInterval" type="string" className="form-control" name="notifInterval" />
              </AvGroup>
              <AvGroup>
                <Label for="offer-company">Company</Label>
                <AvInput id="offer-company" data-cy="company" type="select" className="form-control" name="companyId">
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
              <Button tag={Link} id="cancel-save" to="/offer" replace color="info">
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
  offerEntity: storeState.offer.entity,
  loading: storeState.offer.loading,
  updating: storeState.offer.updating,
  updateSuccess: storeState.offer.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfferUpdate);
