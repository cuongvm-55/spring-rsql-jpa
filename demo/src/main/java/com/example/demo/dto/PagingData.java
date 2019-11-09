package com.example.demo.dto;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PagingData {
    private Long offset;
    private Integer limit;
    private Long totalRecord;
    private String sort;
    private String search;

    public static <T> PagingData createFromPage(Page<T> page) {
        PagingData pagingData = new PagingData();
        if (page != null) {
            pagingData.setTotalRecord(page.getTotalElements());
            if (page.getPageable() != null) {
                pagingData.setLimit(page.getPageable().getPageSize());
                pagingData.setOffset(page.getPageable().getOffset());
            }
        }
        return pagingData;
    }
}
