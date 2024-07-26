import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 25872,
  login: '~5}gU{@Ibm\\:ty',
};

export const sampleWithPartialData: IUser = {
  id: 14504,
  login: 'm@ZBsM\\cA\\.0Cywi\\XdF3hx',
};

export const sampleWithFullData: IUser = {
  id: 29325,
  login: 'y4g3s',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
