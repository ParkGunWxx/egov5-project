package com.example.egov.repository;

import com.example.egov.entity.SampleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

    List<SampleEntity> findByUseAtOrderByRegDtDesc(String useAt);

    Page<SampleEntity> findBySjContainingAndUseAt(String sj, String useAt, Pageable pageable);

    Optional<SampleEntity> findBySampleIdAndUseAt(Long sampleId, String useAt);

    @Query("SELECT s FROM SampleEntity s WHERE s.useAt = 'Y' ORDER BY s.regDt DESC")
    List<SampleEntity> findAllActive();

    @Query(
        value = "SELECT * FROM TB_SAMPLE WHERE USE_AT = 'Y' ORDER BY REG_DT DESC",
        countQuery = "SELECT COUNT(*) FROM TB_SAMPLE WHERE USE_AT = 'Y'",
        nativeQuery = true
    )
    Page<SampleEntity> findActiveNative(Pageable pageable);

    @Modifying
    @Query("UPDATE SampleEntity s SET s.inqireCo = s.inqireCo + 1 WHERE s.sampleId = :sampleId")
    int increaseInqireCo(@Param("sampleId") Long sampleId);
}
