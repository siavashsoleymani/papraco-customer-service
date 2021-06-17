import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFactureItem, defaultValue } from 'app/shared/model/facture-item.model';

export const ACTION_TYPES = {
  FETCH_FACTUREITEM_LIST: 'factureItem/FETCH_FACTUREITEM_LIST',
  FETCH_FACTUREITEM: 'factureItem/FETCH_FACTUREITEM',
  CREATE_FACTUREITEM: 'factureItem/CREATE_FACTUREITEM',
  UPDATE_FACTUREITEM: 'factureItem/UPDATE_FACTUREITEM',
  PARTIAL_UPDATE_FACTUREITEM: 'factureItem/PARTIAL_UPDATE_FACTUREITEM',
  DELETE_FACTUREITEM: 'factureItem/DELETE_FACTUREITEM',
  RESET: 'factureItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFactureItem>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FactureItemState = Readonly<typeof initialState>;

// Reducer

export default (state: FactureItemState = initialState, action): FactureItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FACTUREITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FACTUREITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FACTUREITEM):
    case REQUEST(ACTION_TYPES.UPDATE_FACTUREITEM):
    case REQUEST(ACTION_TYPES.DELETE_FACTUREITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FACTUREITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FACTUREITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FACTUREITEM):
    case FAILURE(ACTION_TYPES.CREATE_FACTUREITEM):
    case FAILURE(ACTION_TYPES.UPDATE_FACTUREITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FACTUREITEM):
    case FAILURE(ACTION_TYPES.DELETE_FACTUREITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FACTUREITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FACTUREITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FACTUREITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_FACTUREITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FACTUREITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FACTUREITEM):
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

const apiUrl = 'api/facture-items';

// Actions

export const getEntities: ICrudGetAllAction<IFactureItem> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FACTUREITEM_LIST,
    payload: axios.get<IFactureItem>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFactureItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FACTUREITEM,
    payload: axios.get<IFactureItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFactureItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FACTUREITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFactureItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FACTUREITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFactureItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FACTUREITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFactureItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FACTUREITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
