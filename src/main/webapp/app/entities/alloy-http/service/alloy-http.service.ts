import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlloyHttp, NewAlloyHttp } from '../alloy-http.model';

export type PartialUpdateAlloyHttp = Partial<IAlloyHttp> & Pick<IAlloyHttp, 'id'>;

type RestOf<T extends IAlloyHttp | NewAlloyHttp> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

export type RestAlloyHttp = RestOf<IAlloyHttp>;

export type NewRestAlloyHttp = RestOf<NewAlloyHttp>;

export type PartialUpdateRestAlloyHttp = RestOf<PartialUpdateAlloyHttp>;

export type EntityResponseType = HttpResponse<IAlloyHttp>;
export type EntityArrayResponseType = HttpResponse<IAlloyHttp[]>;

@Injectable({ providedIn: 'root' })
export class AlloyHttpService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/alloy-https');

  create(alloyHttp: NewAlloyHttp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alloyHttp);
    return this.http
      .post<RestAlloyHttp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(alloyHttp: IAlloyHttp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alloyHttp);
    return this.http
      .put<RestAlloyHttp>(`${this.resourceUrl}/${this.getAlloyHttpIdentifier(alloyHttp)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(alloyHttp: PartialUpdateAlloyHttp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alloyHttp);
    return this.http
      .patch<RestAlloyHttp>(`${this.resourceUrl}/${this.getAlloyHttpIdentifier(alloyHttp)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAlloyHttp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAlloyHttp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAlloyHttpIdentifier(alloyHttp: Pick<IAlloyHttp, 'id'>): number {
    return alloyHttp.id;
  }

  compareAlloyHttp(o1: Pick<IAlloyHttp, 'id'> | null, o2: Pick<IAlloyHttp, 'id'> | null): boolean {
    return o1 && o2 ? this.getAlloyHttpIdentifier(o1) === this.getAlloyHttpIdentifier(o2) : o1 === o2;
  }

  addAlloyHttpToCollectionIfMissing<Type extends Pick<IAlloyHttp, 'id'>>(
    alloyHttpCollection: Type[],
    ...alloyHttpsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const alloyHttps: Type[] = alloyHttpsToCheck.filter(isPresent);
    if (alloyHttps.length > 0) {
      const alloyHttpCollectionIdentifiers = alloyHttpCollection.map(alloyHttpItem => this.getAlloyHttpIdentifier(alloyHttpItem));
      const alloyHttpsToAdd = alloyHttps.filter(alloyHttpItem => {
        const alloyHttpIdentifier = this.getAlloyHttpIdentifier(alloyHttpItem);
        if (alloyHttpCollectionIdentifiers.includes(alloyHttpIdentifier)) {
          return false;
        }
        alloyHttpCollectionIdentifiers.push(alloyHttpIdentifier);
        return true;
      });
      return [...alloyHttpsToAdd, ...alloyHttpCollection];
    }
    return alloyHttpCollection;
  }

  protected convertDateFromClient<T extends IAlloyHttp | NewAlloyHttp | PartialUpdateAlloyHttp>(alloyHttp: T): RestOf<T> {
    return {
      ...alloyHttp,
      createTime: alloyHttp.createTime?.toJSON() ?? null,
      updateTime: alloyHttp.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAlloyHttp: RestAlloyHttp): IAlloyHttp {
    return {
      ...restAlloyHttp,
      createTime: restAlloyHttp.createTime ? dayjs(restAlloyHttp.createTime) : undefined,
      updateTime: restAlloyHttp.updateTime ? dayjs(restAlloyHttp.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAlloyHttp>): HttpResponse<IAlloyHttp> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAlloyHttp[]>): HttpResponse<IAlloyHttp[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
