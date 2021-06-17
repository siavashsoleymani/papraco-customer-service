import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileDetail = (props: IFileDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fileEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileDetailsHeading">File</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fileEntity.id}</dd>
          <dt>
            <span id="path">Path</span>
          </dt>
          <dd>{fileEntity.path}</dd>
          <dt>
            <span id="file">File</span>
          </dt>
          <dd>
            {fileEntity.file ? (
              <div>
                {fileEntity.fileContentType ? <a onClick={openFile(fileEntity.fileContentType, fileEntity.file)}>Open&nbsp;</a> : null}
                <span>
                  {fileEntity.fileContentType}, {byteSize(fileEntity.file)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Staff</dt>
          <dd>{fileEntity.staff ? fileEntity.staff.id : ''}</dd>
          <dt>Offer</dt>
          <dd>{fileEntity.offer ? fileEntity.offer.id : ''}</dd>
          <dt>Product</dt>
          <dd>{fileEntity.product ? fileEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file/${fileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ file }: IRootState) => ({
  fileEntity: file.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileDetail);
