package com.example.egov.web;

import com.example.egov.common.ApiResponse;
import com.example.egov.service.SampleService;
import com.example.egov.web.dto.SampleRequestDto;
import com.example.egov.web.dto.SampleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 샘플 REST Controller
 * 전자정부프레임워크 5.0 표준 URL 패턴 적용
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /** 목록 조회 - GET /api/v1/samples */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SampleResponseDto>>> selectSampleList() {
        return ResponseEntity.ok(ApiResponse.success(sampleService.selectSampleList()));
    }

    /** 페이징 목록 - GET /api/v1/samples/page?sj=검색어&page=0&size=10 */
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<SampleResponseDto>>> selectSamplePageList(
        @RequestParam(required = false) String sj,
        @PageableDefault(size = 10, sort = "regDt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(sampleService.selectSamplePageList(sj, pageable)));
    }

    /** 단건 조회 - GET /api/v1/samples/{sampleId} */
    @GetMapping("/{sampleId}")
    public ResponseEntity<ApiResponse<SampleResponseDto>> selectSample(@PathVariable Long sampleId) {
        return ResponseEntity.ok(ApiResponse.success(sampleService.selectSample(sampleId)));
    }

    /** 등록 - POST /api/v1/samples */
    @PostMapping
    public ResponseEntity<ApiResponse<SampleResponseDto>> insertSample(
        @Valid @RequestBody SampleRequestDto dto
    ) {
        return ResponseEntity.ok(ApiResponse.success(sampleService.insertSample(dto)));
    }

    /** 수정 - PUT /api/v1/samples/{sampleId} */
    @PutMapping("/{sampleId}")
    public ResponseEntity<ApiResponse<SampleResponseDto>> updateSample(
        @PathVariable Long sampleId,
        @Valid @RequestBody SampleRequestDto dto
    ) {
        return ResponseEntity.ok(ApiResponse.success(sampleService.updateSample(sampleId, dto)));
    }

    /** 삭제 (논리 삭제) - DELETE /api/v1/samples/{sampleId} */
    @DeleteMapping("/{sampleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSample(@PathVariable Long sampleId) {
        sampleService.deleteSample(sampleId);
        return ResponseEntity.ok(ApiResponse.success("삭제되었습니다."));
    }
}
