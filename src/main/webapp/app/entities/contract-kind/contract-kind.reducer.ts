import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IContractKind, defaultValue } from 'app/shared/model/contract-kind.model';

export const ACTION_TYPES = {
  FETCH_CONTRACTKIND_LIST: 'contractKind/FETCH_CONTRACTKIND_LIST',
  FETCH_CONTRACTKIND: 'contractKind/FETCH_CONTRACTKIND',
  CREATE_CONTRACTKIND: 'contractKind/CREATE_CONTRACTKIND',
  UPDATE_CONTRACTKIND: 'contractKind/UPDATE_CONTRACTKIND',
  PARTIAL_UPDATE_CONTRACTKIND: 'contractKind/PARTIAL_UPDATE_CONTRACTKIND',
  DELETE_CONTRACTKIND: 'contractKind/DELETE_CONTRACTKIND',
  RESET: 'contractKind/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IContractKind>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ContractKindState = Readonly<typeof initialState>;

// Reducer

export default (state: ContractKindState = initialState, action): ContractKindState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONTRACTKIND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONTRACTKIND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONTRACTKIND):
    case REQUEST(ACTION_TYPES.UPDATE_CONTRACTKIND):
    case REQUEST(ACTION_TYPES.DELETE_CONTRACTKIND):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CONTRACTKIND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONTRACTKIND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONTRACTKIND):
    case FAILURE(ACTION_TYPES.CREATE_CONTRACTKIND):
    case FAILURE(ACTION_TYPES.UPDATE_CONTRACTKIND):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CONTRACTKIND):
    case FAILURE(ACTION_TYPES.DELETE_CONTRACTKIND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTRACTKIND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTRACTKIND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONTRACTKIND):
    case SUCCESS(ACTION_TYPES.UPDATE_CONTRACTKIND):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CONTRACTKIND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONTRACTKIND):
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

const apiUrl = 'api/contract-kinds';

// Actions

export const getEntities: ICrudGetAllAction<IContractKind> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONTRACTKIND_LIST,
    payload: axios.get<IContractKind>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IContractKind> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONTRACTKIND,
    payload: axios.get<IContractKind>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IContractKind> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONTRACTKIND,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IContractKind> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONTRACTKIND,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IContractKind> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CONTRACTKIND,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IContractKind> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONTRACTKIND,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
