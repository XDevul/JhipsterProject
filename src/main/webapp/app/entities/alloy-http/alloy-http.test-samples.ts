import dayjs from 'dayjs/esm';

import { IAlloyHttp, NewAlloyHttp } from './alloy-http.model';

export const sampleWithRequiredData: IAlloyHttp = {
  id: 17183,
  name: 'reply who',
  address: 'since majestically',
  modul: 'gape kissingly',
  createTime: dayjs('2024-07-25T22:42'),
};

export const sampleWithPartialData: IAlloyHttp = {
  id: 23015,
  name: 'sometimes',
  address: 'angle wastebasket',
  modul: 'why hallway',
  api: 'until knowingly',
  env: 'and recreate',
  hostname: 'notwithstanding',
  createTime: dayjs('2024-07-25T12:00'),
  updateTime: dayjs('2024-07-25T12:48'),
};

export const sampleWithFullData: IAlloyHttp = {
  id: 27937,
  name: 'next withstand however',
  address: 'scrawl last now',
  modul: 'pink from irritating',
  api: 'heyday twin',
  env: 'high arid preview',
  hostname: 'pish frisk',
  createTime: dayjs('2024-07-26T00:23'),
  updateTime: dayjs('2024-07-25T06:57'),
};

export const sampleWithNewData: NewAlloyHttp = {
  name: 'gadzooks apud ferociously',
  address: 'medical',
  modul: 'miniature which gree',
  createTime: dayjs('2024-07-25T18:03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
