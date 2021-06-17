import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-comment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserCommentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserCommentDetail = (props: IUserCommentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userCommentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userCommentDetailsHeading">UserComment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userCommentEntity.id}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{userCommentEntity.comment}</dd>
          <dt>
            <span id="score">Score</span>
          </dt>
          <dd>{userCommentEntity.score}</dd>
          <dt>Company</dt>
          <dd>{userCommentEntity.company ? userCommentEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-comment/${userCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userComment }: IRootState) => ({
  userCommentEntity: userComment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserCommentDetail);
