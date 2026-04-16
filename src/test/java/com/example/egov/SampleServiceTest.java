package com.example.egov;

import com.example.egov.entity.SampleEntity;
import com.example.egov.repository.SampleRepository;
import com.example.egov.service.SampleService;
import com.example.egov.web.dto.SampleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SampleServiceTest {

    @Autowired SampleService sampleService;
    @Autowired SampleRepository sampleRepository;

    @Test
    @DisplayName("게시글 등록 후 목록 조회 테스트")
    void insertAndSelectTest() {
        SampleEntity entity = SampleEntity.builder()
            .sj("테스트 제목").cn("테스트 내용").wrterNm("홍길동").build();
        sampleRepository.save(entity);

        var list = sampleService.selectSampleList();
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getSj()).isNotBlank();
    }

    @Test
    @DisplayName("단건 조회 테스트")
    void selectSampleTest() {
        SampleEntity entity = SampleEntity.builder()
            .sj("단건조회 테스트").cn("내용").wrterNm("테스터").build();
        SampleEntity saved = sampleRepository.save(entity);

        SampleResponseDto dto = sampleService.selectSample(saved.getSampleId());
        assertThat(dto.getSj()).isEqualTo("단건조회 테스트");
        assertThat(dto.getUseAt()).isEqualTo("Y");
    }
}
