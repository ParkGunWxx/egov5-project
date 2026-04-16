package com.example.egov.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통 Auditing 기본 엔티티
 * 전자정부프레임워크 5.0 표준 컬럼명 적용
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "REG_DT", nullable = false, updatable = false)
    private LocalDateTime regDt;

    @CreatedBy
    @Column(name = "RGTR", nullable = false, updatable = false, length = 50)
    private String rgtr;  // 등록자 (전자정부 표준: RGTR)

    @LastModifiedDate
    @Column(name = "MDFCN_DT")
    private LocalDateTime mdfcnDt;  // 수정일시 (전자정부 표준: MDFCN_DT)

    @LastModifiedBy
    @Column(name = "MDFR", length = 50)
    private String mdfr;  // 수정자 (전자정부 표준: MDFR)
}
