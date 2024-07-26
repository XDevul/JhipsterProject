package com.jhipster.test.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.test.domain.AlloyHttp} entity. This class is used
 * in {@link com.jhipster.test.web.rest.AlloyHttpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alloy-https?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlloyHttpCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter modul;

    private StringFilter api;

    private StringFilter env;

    private StringFilter hostname;

    private InstantFilter createTime;

    private InstantFilter updateTime;

    private Boolean distinct;

    public AlloyHttpCriteria() {}

    public AlloyHttpCriteria(AlloyHttpCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.address = other.optionalAddress().map(StringFilter::copy).orElse(null);
        this.modul = other.optionalModul().map(StringFilter::copy).orElse(null);
        this.api = other.optionalApi().map(StringFilter::copy).orElse(null);
        this.env = other.optionalEnv().map(StringFilter::copy).orElse(null);
        this.hostname = other.optionalHostname().map(StringFilter::copy).orElse(null);
        this.createTime = other.optionalCreateTime().map(InstantFilter::copy).orElse(null);
        this.updateTime = other.optionalUpdateTime().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AlloyHttpCriteria copy() {
        return new AlloyHttpCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAddress() {
        return address;
    }

    public Optional<StringFilter> optionalAddress() {
        return Optional.ofNullable(address);
    }

    public StringFilter address() {
        if (address == null) {
            setAddress(new StringFilter());
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getModul() {
        return modul;
    }

    public Optional<StringFilter> optionalModul() {
        return Optional.ofNullable(modul);
    }

    public StringFilter modul() {
        if (modul == null) {
            setModul(new StringFilter());
        }
        return modul;
    }

    public void setModul(StringFilter modul) {
        this.modul = modul;
    }

    public StringFilter getApi() {
        return api;
    }

    public Optional<StringFilter> optionalApi() {
        return Optional.ofNullable(api);
    }

    public StringFilter api() {
        if (api == null) {
            setApi(new StringFilter());
        }
        return api;
    }

    public void setApi(StringFilter api) {
        this.api = api;
    }

    public StringFilter getEnv() {
        return env;
    }

    public Optional<StringFilter> optionalEnv() {
        return Optional.ofNullable(env);
    }

    public StringFilter env() {
        if (env == null) {
            setEnv(new StringFilter());
        }
        return env;
    }

    public void setEnv(StringFilter env) {
        this.env = env;
    }

    public StringFilter getHostname() {
        return hostname;
    }

    public Optional<StringFilter> optionalHostname() {
        return Optional.ofNullable(hostname);
    }

    public StringFilter hostname() {
        if (hostname == null) {
            setHostname(new StringFilter());
        }
        return hostname;
    }

    public void setHostname(StringFilter hostname) {
        this.hostname = hostname;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public Optional<InstantFilter> optionalCreateTime() {
        return Optional.ofNullable(createTime);
    }

    public InstantFilter createTime() {
        if (createTime == null) {
            setCreateTime(new InstantFilter());
        }
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public Optional<InstantFilter> optionalUpdateTime() {
        return Optional.ofNullable(updateTime);
    }

    public InstantFilter updateTime() {
        if (updateTime == null) {
            setUpdateTime(new InstantFilter());
        }
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlloyHttpCriteria that = (AlloyHttpCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(modul, that.modul) &&
            Objects.equals(api, that.api) &&
            Objects.equals(env, that.env) &&
            Objects.equals(hostname, that.hostname) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, modul, api, env, hostname, createTime, updateTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlloyHttpCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalAddress().map(f -> "address=" + f + ", ").orElse("") +
            optionalModul().map(f -> "modul=" + f + ", ").orElse("") +
            optionalApi().map(f -> "api=" + f + ", ").orElse("") +
            optionalEnv().map(f -> "env=" + f + ", ").orElse("") +
            optionalHostname().map(f -> "hostname=" + f + ", ").orElse("") +
            optionalCreateTime().map(f -> "createTime=" + f + ", ").orElse("") +
            optionalUpdateTime().map(f -> "updateTime=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
