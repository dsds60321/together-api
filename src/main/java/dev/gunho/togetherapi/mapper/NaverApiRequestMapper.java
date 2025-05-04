package dev.gunho.togetherapi.mapper;

import dev.gunho.togetherapi.dto.thirdparty.NaverApiRequest;
import org.mapstruct.Mapper;
import org.springframework.web.reactive.function.server.ServerRequest;

@Mapper(componentModel = "spring")
public interface NaverApiRequestMapper {

    default NaverApiRequest toNaverApiRequest(ServerRequest request) {
        return new NaverApiRequest(
                request.queryParam("query").orElse(""),
                request.queryParam("display").orElse("10"),
                request.queryParam("start").orElse("1"),
                request.queryParam("sort").orElse("date")
        );
    }
}
