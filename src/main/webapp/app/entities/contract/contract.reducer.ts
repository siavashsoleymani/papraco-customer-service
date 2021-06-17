import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IContract, defaultValue } from 'app/shared/model/contract.model';

export const ACTION_TYPES = {
  FETCH_CONTRACT_LIST: 'contract/FETCH_CONTRACT_LIST',
  FETCH_CONTRACT: 'contract/FETCH_CONTRACT',
  CREATE_CONTRACT: 'contract/CREATE_CONTRACT',
  UPDATE_CONTRACT: 'contract/UPDATE_CONTRACT',
  PARTIAL_UPDATE_CONTRACT: 'contract/PARTIAL_UPDATE_CONTRACT',
  DELETE_CONTRACT: 'contract/DELETE_CONTRACT',
  SET_BLOB: 'contract/SET_BLOB',
  RESET: 'contract/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IContract>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ContractState = Readonly<typeof initialState>;

// Reducer

export default (state: ContractState = initialState, action): ContractState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONTRACT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONTRACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONTRACT):
    case REQUEST(ACTION_TYPES.UPDATE_CONTRACT):
    case REQUEST(ACTION_TYPES.DELETE_CONTRACT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CONTRACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONTRACT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONTRACT):
    case FAILURE(ACTION_TYPES.CREATE_CONTRACT):
    case FAILURE(ACTION_TYPES.UPDATE_CONTRACT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CONTRACT):
    case FAILURE(ACTION_TYPES.DELETE_CONTRACT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTRACT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTRACT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONTRACT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONTRACT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CONTRACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONTRACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/contracts';

// Actions

export const getEntities: ICrudGetAllAction<IContract> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONTRACT_LIST,
    payload: axios.get<IContract>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IContract> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONTRACT,
    payload: axios.get<IContract>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IContract> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONTRACT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IContract> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONTRACT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IContract> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CONTRACT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IContract> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONTRACT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
