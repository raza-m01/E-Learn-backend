package com.elearn.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// made for pagination response

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResponse<T> {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private boolean isLast;
    private int totalPages;
    private List<T> content;
}
