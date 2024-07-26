package com.jhipster.test.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlloyHttp.
 */
@Entity
@Table(name = "alloy_http")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlloyHttp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Job Name
     */
    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    /**
     * 監控的URL
     */
    @NotNull
    @Size(max = 2048)
    @Column(name = "address", length = 2048, nullable = false)
    private String address;

    /**
     * 監控方式的設定檔
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "modul", length = 20, nullable = false)
    private String modul;

    /**
     * 自定義欄位，方便後續業務邏輯
     */
    @Size(max = 20)
    @Column(name = "api", length = 20)
    private String api;

    /**
     * 自定義欄位，方便後續業務邏輯
     */
    @Size(max = 20)
    @Column(name = "env", length = 20)
    private String env;

    /**
     * 自定義欄位，方便後續業務邏輯
     */
    @Size(max = 2048)
    @Column(name = "hostname", length = 2048)
    private String hostname;

    /**
     * 建立日
     */
    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 更新日
     */
    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlloyHttp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AlloyHttp name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public AlloyHttp address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getModul() {
        return this.modul;
    }

    public AlloyHttp modul(String modul) {
        this.setModul(modul);
        return this;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getApi() {
        return this.api;
    }

    public AlloyHttp api(String api) {
        this.setApi(api);
        return this;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getEnv() {
        return this.env;
    }

    public AlloyHttp env(String env) {
        this.setEnv(env);
        return this;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getHostname() {
        return this.hostname;
    }

    public AlloyHttp hostname(String hostname) {
        this.setHostname(hostname);
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public AlloyHttp createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public AlloyHttp updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlloyHttp)) {
            return false;
        }
        return getId() != null && getId().equals(((AlloyHttp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlloyHttp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", modul='" + getModul() + "'" +
            ", api='" + getApi() + "'" +
            ", env='" + getEnv() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
