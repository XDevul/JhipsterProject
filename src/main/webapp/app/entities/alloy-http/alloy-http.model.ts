import dayjs from 'dayjs/esm';

export interface IAlloyHttp {
  id: number;
  name?: string | null;
  address?: string | null;
  modul?: string | null;
  api?: string | null;
  env?: string | null;
  hostname?: string | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
}

export type NewAlloyHttp = Omit<IAlloyHttp, 'id'> & { id: null };
