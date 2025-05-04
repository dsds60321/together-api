package dev.gunho.togetherapi.dto.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverApiRequest {

    private String query;
    private String display;
    private String start;
    private String sort;
}
