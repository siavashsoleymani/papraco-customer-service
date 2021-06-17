import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContract } from 'app/shared/model/contract.model';
import { getEntities as getContracts } from 'app/entities/contract/contract.reducer';
import { getEntity, updateEntity, createEntity, reset } from './contract-kind.reducer';
import { IContractKind } from 'app/shared/model/contract-kind.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContractKindUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContractKindUpdate = (props: IContractKindUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { contractKindEntity, contracts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/contract-kind' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getContracts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...contractKindEntity,
        ...values,
        contract: contracts.find(it => it.id.toString() === values.contractId.toString()),
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
          <h2 id="customerserviceApp.contractKind.home.createOrEditLabel" data-cy="ContractKindCreateUpdateHeading">
            Create or edit a ContractKind
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : contractKindEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="contract-kind-id">ID</Label>
                  <AvInput id="contract-kind-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="contract-kind-name">
                  Name
                </Label>
                <AvField id="contract-kind-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="contract-kind-contract">Contract</Label>
                <AvInput id="contract-kind-contract" data-cy="contract" type="select" className="form-control" name="contractId">
                  <option value="" key="0" />
                  {contracts
                    ? contracts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/contract-kind" replace color="info">
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
  contracts: storeState.contract.entities,
  contractKindEntity: storeState.contractKind.entity,
  loading: storeState.contractKind.loading,
  updating: storeState.contractKind.updating,
  updateSuccess: storeState.contractKind.updateSuccess,
});

const mapDispatchToProps = {
  getContracts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContractKindUpdate);
