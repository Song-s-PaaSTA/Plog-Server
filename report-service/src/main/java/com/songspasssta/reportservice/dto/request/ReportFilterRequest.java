package com.songspasssta.reportservice.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportFilterRequest {
    private List<String> regions;
    private String sort;
    private List<String> statuses;
}
