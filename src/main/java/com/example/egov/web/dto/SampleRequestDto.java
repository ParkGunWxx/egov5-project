package com.example.egov.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 샘플 등록/수정 요청 DTO
 */
@Getter
@NoArgsConstructor
public class SampleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이내로 입력하세요.")
    private String sj;       // 제목 (전자정부 표준: SJ)

    @Size(max = 4000, message = "내용은 4000자 이내로 입력하세요.")
    private String cn;       // 내용 (전자정부 표준: CN)

    @Size(max = 100, message = "작성자명은 100자 이내로 입력하세요.")
    private String wrterNm;  // 작성자명
}
