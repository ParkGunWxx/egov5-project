package com.example.egov.service.impl;

import com.example.egov.entity.SampleEntity;
import com.example.egov.repository.SampleRepository;
import com.example.egov.service.SampleService;
import com.example.egov.web.dto.SampleRequestDto;
import com.example.egov.web.dto.SampleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 샘플 서비스 구현체
 *
 * eGovFrame 5.0 변경점:
 *   - EgovAbstractServiceImpl 제거 → 순수 Spring @Service 사용
 *   - 메서드명은 전자정부 표준 명명규칙 유지 (select/insert/update/delete)
 */
@Slf4j
@Service("sampleService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;

    @Override
    public List<SampleResponseDto> selectSampleList() {
        log.debug("[SampleService] selectSampleList");
        return sampleRepository.findByUseAtOrderByRegDtDesc("Y")
            .stream().map(SampleResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public Page<SampleResponseDto> selectSamplePageList(String sj, Pageable pageable) {
        log.debug("[SampleService] selectSamplePageList - keyword: {}", sj);
        Page<SampleEntity> page = (sj != null && !sj.isBlank())
            ? sampleRepository.findBySjContainingAndUseAt(sj, "Y", pageable)
            : sampleRepository.findActiveNative(pageable);
        return page.map(SampleResponseDto::from);
//        테스트용 주석
    }

    @Override
    public SampleResponseDto selectSample(Long sampleId) {
        log.debug("[SampleService] selectSample - sampleId: {}", sampleId);
        SampleEntity entity = sampleRepository.findBySampleIdAndUseAt(sampleId, "Y")
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + sampleId));
        sampleRepository.increaseInqireCo(sampleId);
        return SampleResponseDto.from(entity);
    }

    @Override
    @Transactional
    public SampleResponseDto insertSample(SampleRequestDto dto) {
        log.debug("[SampleService] insertSample - sj: {}", dto.getSj());
        SampleEntity entity = SampleEntity.builder()
            .sj(dto.getSj()).cn(dto.getCn()).wrterNm(dto.getWrterNm()).build();
        return SampleResponseDto.from(sampleRepository.save(entity));
    }

    @Override
    @Transactional
    public SampleResponseDto updateSample(Long sampleId, SampleRequestDto dto) {
        log.debug("[SampleService] updateSample - sampleId: {}", sampleId);
        SampleEntity entity = sampleRepository.findBySampleIdAndUseAt(sampleId, "Y")
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + sampleId));
        entity.update(dto.getSj(), dto.getCn());
        return SampleResponseDto.from(entity);
    }

    @Override
    @Transactional
    public void deleteSample(Long sampleId) {
        log.debug("[SampleService] deleteSample - sampleId: {}", sampleId);
        SampleEntity entity = sampleRepository.findBySampleIdAndUseAt(sampleId, "Y")
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + sampleId));
        entity.delete();
    }
}
