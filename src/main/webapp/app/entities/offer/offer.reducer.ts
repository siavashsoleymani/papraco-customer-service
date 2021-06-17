import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOffer, defaultValue } from 'app/shared/model/offer.model';

export const ACTION_TYPES = {
  FETCH_OFFER_LIST: 'offer/FETCH_OFFER_LIST',
  FETCH_OFFER: 'offer/FETCH_OFFER',
  CREATE_OFFER: 'offer/CREATE_OFFER',
  UPDATE_OFFER: 'offer/UPDATE_OFFER',
  PARTIAL_UPDATE_OFFER: 'offer/PARTIAL_UPDATE_OFFER',
  DELETE_OFFER: 'offer/DELETE_OFFER',
  SET_BLOB: 'offer/SET_BLOB',
  RESET: 'offer/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOffer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OfferState = Readonly<typeof initialState>;

// Reducer

export default (state: OfferState = initialState, action): OfferState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OFFER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OFFER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OFFER):
    case REQUEST(ACTION_TYPES.UPDATE_OFFER):
    case REQUEST(ACTION_TYPES.DELETE_OFFER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OFFER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OFFER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OFFER):
    case FAILURE(ACTION_TYPES.CREATE_OFFER):
    case FAILURE(ACTION_TYPES.UPDATE_OFFER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OFFER):
    case FAILURE(ACTION_TYPES.DELETE_OFFER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OFFER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_OFFER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OFFER):
    case SUCCESS(ACTION_TYPES.UPDATE_OFFER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OFFER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OFFER):
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

const apiUrl = 'api/offers';

// Actions

export const getEntities: ICrudGetAllAction<IOffer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OFFER_LIST,
    payload: axios.get<IOffer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOffer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OFFER,
    payload: axios.get<IOffer>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOffer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OFFER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOffer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OFFER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOffer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OFFER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOffer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OFFER,
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
