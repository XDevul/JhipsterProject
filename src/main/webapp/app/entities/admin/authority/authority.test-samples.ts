import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '625f89b3-801f-417f-9b27-dd8ac8686da2',
};

export const sampleWithPartialData: IAuthority = {
  name: 'faef96b8-7906-43e9-8f11-6dc1d0bca401',
};

export const sampleWithFullData: IAuthority = {
  name: '0bfdebeb-501f-494f-b254-6378349d7c59',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
