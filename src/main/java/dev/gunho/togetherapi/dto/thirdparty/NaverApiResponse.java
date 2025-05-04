package dev.gunho.togetherapi.dto.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverApiResponse<T> {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<T> items;
}