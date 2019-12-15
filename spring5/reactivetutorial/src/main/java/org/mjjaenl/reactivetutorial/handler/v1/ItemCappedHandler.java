package org.mjjaenl.reactivetutorial.handler.v1;

import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.service.ItemCappedReactiveService;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mjjaenl.reactivetutorial.utils.Utils.convertMultiValueMapStringStringToMap;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class ItemCappedHandler {
    @Autowired
    private ItemCappedReactiveService itemCappedReactiveService;

    public Mono<ServerResponse> getAllStream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
                .body(itemCappedReactiveService.findAll(), ItemCapped.class);
    }
}
