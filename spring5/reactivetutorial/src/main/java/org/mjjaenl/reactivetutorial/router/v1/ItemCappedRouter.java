package org.mjjaenl.reactivetutorial.router.v1;

import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_STREAM_URI_V1_FUNCTIONAL;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.mjjaenl.reactivetutorial.handler.v1.ItemCappedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ItemCappedRouter {
    @Bean
    public RouterFunction<ServerResponse> getAllStream(ItemCappedHandler itemCappedHandler) {
        return route(GET(ITEM_STREAM_URI_V1_FUNCTIONAL)
                .and(accept(MediaType.APPLICATION_JSON)), itemCappedHandler::getAllStream);
    }
}
