package com.example.egov.entity;

import com.example.egov.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 샘플 Entity
 * - Oracle SEQUENCE 기반 PK 채번
 * - 전자정부프레임워크 5.0 표준 컬럼 네이밍 적용
 *
 * DDL: CREATE SEQUENCE SAMPLE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE;
 *      → src/main/resources/sql/ddl.sql 참조
 */
@Entity
@Table(name = "TB_SAMPLE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"content"})
public class SampleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_SEQ_GEN")
    @SequenceGenerator(
        name      = "SAMPLE_SEQ_GEN",
        sequenceName = "SAMPLE_SEQ",
        allocationSize = 1
    )
    @Column(name = "SAMPLE_ID")
    private Long sampleId;

    @Column(name = "SJ", nullable = false, length = 200)   // SJ: 제목 (전자정부 표준)
    private String sj;

    @Column(name = "CN", length = 4000)                    // CN: 내용
    private String cn;

    @Column(name = "WRTER_NM", length = 100)               // 작성자명
    private String wrterNm;

    @Column(name = "USE_AT", length = 1, nullable = false) // 사용여부 (Y/N)
    private String useAt;

    @Column(name = "INQIRE_CO")                            // 조회수
    private Integer inqireCo;

    // ====================================
    // 생성 팩토리
    // ====================================
    @Builder
    public SampleEntity(String sj, String cn, String wrterNm) {
        this.sj      = sj;
        this.cn      = cn;
        this.wrterNm = wrterNm;
        this.useAt   = "Y";
        this.inqireCo = 0;
    }

    // ====================================
    // 비즈니스 메서드
    // ====================================
    public void update(String sj, String cn) {
        this.sj = sj;
        this.cn = cn;
    }

    public void delete() {
        this.useAt = "N";
    }

    public void increaseInqireCo() {
        this.inqireCo = (this.inqireCo == null ? 0 : this.inqireCo) + 1;
    }
}
