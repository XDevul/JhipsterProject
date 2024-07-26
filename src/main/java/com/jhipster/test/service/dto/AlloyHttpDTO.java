package com.jhipster.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.test.domain.AlloyHttp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlloyHttpDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    @Schema(description = "Job Name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Size(max = 2048)
    @Schema(description = "監控的URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @NotNull
    @Size(max = 20)
    @Schema(description = "監控方式的設定檔", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modul;

    @Size(max = 20)
    @Schema(description = "自定義欄位，方便後續業務邏輯")
    private String api;

    @Size(max = 20)
    @Schema(description = "自定義欄位，方便後續業務邏輯")
    private String env;

    @Size(max = 2048)
    @Schema(description = "自定義欄位，方便後續業務邏輯")
    private String hostname;

    @NotNull
    @Schema(description = "建立日", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant createTime;

    @Schema(description = "更新日")
    private Instant updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlloyHttpDTO)) {
            return false;
        }

        AlloyHttpDTO alloyHttpDTO = (AlloyHttpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alloyHttpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlloyHttpDTO{" +
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
