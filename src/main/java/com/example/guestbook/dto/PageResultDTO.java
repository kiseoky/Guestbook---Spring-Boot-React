package com.example.guestbook.dto;

import com.example.guestbook.entity.Guestbook;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;

    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;

    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        setPageList(result);
    }


    private void setPageList(Page<EN> result) {
        Pageable pageable = result.getPageable();

        this.totalPage = result.getTotalPages();
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        this.end = Math.min(this.totalPage, (int) (Math.ceil(this.page / 10.0)) * 10);
        this.start = (int) ((this.page - 0.1) / 10.0) * 10 + 1;

        this.prev = start > 1;
        this.next = totalPage > end;

        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
