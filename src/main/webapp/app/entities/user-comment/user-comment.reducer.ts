import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserComment, defaultValue } from 'app/shared/model/user-comment.model';

export const ACTION_TYPES = {
  FETCH_USERCOMMENT_LIST: 'userComment/FETCH_USERCOMMENT_LIST',
  FETCH_USERCOMMENT: 'userComment/FETCH_USERCOMMENT',
  CREATE_USERCOMMENT: 'userComment/CREATE_USERCOMMENT',
  UPDATE_USERCOMMENT: 'userComment/UPDATE_USERCOMMENT',
  PARTIAL_UPDATE_USERCOMMENT: 'userComment/PARTIAL_UPDATE_USERCOMMENT',
  DELETE_USERCOMMENT: 'userComment/DELETE_USERCOMMENT',
  RESET: 'userComment/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserComment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserCommentState = Readonly<typeof initialState>;

// Reducer

export default (state: UserCommentState = initialState, action): UserCommentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERCOMMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERCOMMENT):
    case REQUEST(ACTION_TYPES.UPDATE_USERCOMMENT):
    case REQUEST(ACTION_TYPES.DELETE_USERCOMMENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_USERCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERCOMMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERCOMMENT):
    case FAILURE(ACTION_TYPES.CREATE_USERCOMMENT):
    case FAILURE(ACTION_TYPES.UPDATE_USERCOMMENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_USERCOMMENT):
    case FAILURE(ACTION_TYPES.DELETE_USERCOMMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERCOMMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERCOMMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERCOMMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_USERCOMMENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_USERCOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERCOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/user-comments';

// Actions

export const getEntities: ICrudGetAllAction<IUserComment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERCOMMENT_LIST,
    payload: axios.get<IUserComment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserComment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERCOMMENT,
    payload: axios.get<IUserComment>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERCOMMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERCOMMENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IUserComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_USERCOMMENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserComment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERCOMMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
