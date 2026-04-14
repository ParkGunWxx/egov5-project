-- ====================================
-- 전자정부프레임워크 5.0 샘플 DDL (Oracle)
-- 표준 컬럼명 적용 (SJ/CN/RGTR/MDFCN_DT 등)
-- ====================================

-- 시퀀스
CREATE SEQUENCE SAMPLE_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 샘플 테이블
CREATE TABLE TB_SAMPLE (
    SAMPLE_ID   NUMBER          NOT NULL,
    SJ          VARCHAR2(200)   NOT NULL,               -- 제목 (Subject)
    CN          VARCHAR2(4000),                         -- 내용 (Contents)
    WRTER_NM    VARCHAR2(100),                          -- 작성자명
    USE_AT      CHAR(1)         DEFAULT 'Y' NOT NULL,  -- 사용여부 (Y/N)
    INQIRE_CO   NUMBER          DEFAULT 0,             -- 조회수
    REG_DT      DATE            DEFAULT SYSDATE NOT NULL,
    RGTR        VARCHAR2(50)    NOT NULL,               -- 등록자 (전자정부 표준)
    MDFCN_DT    DATE,                                   -- 수정일시
    MDFR        VARCHAR2(50),                           -- 수정자
    CONSTRAINT PK_TB_SAMPLE PRIMARY KEY (SAMPLE_ID),
    CONSTRAINT CK_TB_SAMPLE_USE_AT CHECK (USE_AT IN ('Y', 'N'))
);

COMMENT ON TABLE  TB_SAMPLE            IS '샘플 테이블';
COMMENT ON COLUMN TB_SAMPLE.SAMPLE_ID  IS '샘플 ID';
COMMENT ON COLUMN TB_SAMPLE.SJ         IS '제목';
COMMENT ON COLUMN TB_SAMPLE.CN         IS '내용';
COMMENT ON COLUMN TB_SAMPLE.WRTER_NM   IS '작성자명';
COMMENT ON COLUMN TB_SAMPLE.USE_AT     IS '사용여부 (Y:사용, N:미사용)';
COMMENT ON COLUMN TB_SAMPLE.INQIRE_CO  IS '조회수';
COMMENT ON COLUMN TB_SAMPLE.REG_DT     IS '등록일시';
COMMENT ON COLUMN TB_SAMPLE.RGTR       IS '등록자';
COMMENT ON COLUMN TB_SAMPLE.MDFCN_DT   IS '수정일시';
COMMENT ON COLUMN TB_SAMPLE.MDFR       IS '수정자';

-- 인덱스
CREATE INDEX IDX_TB_SAMPLE_USE_AT ON TB_SAMPLE (USE_AT, REG_DT DESC);

-- 테스트 데이터
INSERT INTO TB_SAMPLE (SAMPLE_ID, SJ, CN, WRTER_NM, USE_AT, INQIRE_CO, REG_DT, RGTR)
VALUES (SAMPLE_SEQ.NEXTVAL, '첫 번째 샘플', '전자정부프레임워크 5.0 테스트', '홍길동', 'Y', 0, SYSDATE, 'SYSTEM');

INSERT INTO TB_SAMPLE (SAMPLE_ID, SJ, CN, WRTER_NM, USE_AT, INQIRE_CO, REG_DT, RGTR)
VALUES (SAMPLE_SEQ.NEXTVAL, '두 번째 샘플', 'Spring Boot 3.3 + JPA + Oracle', '이순신', 'Y', 0, SYSDATE, 'SYSTEM');

COMMIT;
