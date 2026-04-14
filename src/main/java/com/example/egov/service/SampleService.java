package com.example.egov.service;

import com.example.egov.web.dto.SampleRequestDto;
import com.example.egov.web.dto.SampleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 샘플 서비스 인터페이스
 * 전자정부프레임워크 5.0 - 인터페이스/구현체 분리 패턴 유지
 */
public interface SampleService {

    List<SampleResponseDto> selectSampleList();

    Page<SampleResponseDto> selectSamplePageList(String sj, Pageable pageable);

    SampleResponseDto selectSample(Long sampleId);

    SampleResponseDto insertSample(SampleRequestDto dto);

    SampleResponseDto updateSample(Long sampleId, SampleRequestDto dto);

    void deleteSample(Long sampleId);
}
