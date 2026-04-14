package com.example.egov.web.dto;

import com.example.egov.entity.SampleEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SampleResponseDto {

    private Long sampleId;
    private String sj;
    private String cn;
    private String wrterNm;
    private String useAt;
    private Integer inqireCo;
    private LocalDateTime regDt;
    private String rgtr;
    private LocalDateTime mdfcnDt;
    private String mdfr;

    public static SampleResponseDto from(SampleEntity e) {
        return SampleResponseDto.builder()
            .sampleId(e.getSampleId())
            .sj(e.getSj())
            .cn(e.getCn())
            .wrterNm(e.getWrterNm())
            .useAt(e.getUseAt())
            .inqireCo(e.getInqireCo())
            .regDt(e.getRegDt())
            .rgtr(e.getRgtr())
            .mdfcnDt(e.getMdfcnDt())
            .mdfr(e.getMdfr())
            .build();
    }
}
