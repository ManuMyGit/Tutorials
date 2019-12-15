package org.mjjaenl.reactivetutorial.service;

import org.mjjaenl.reactivetutorial.model.ItemCapped;
import reactor.core.publisher.Flux;

public interface ItemCappedReactiveService {
    Flux<ItemCapped> findAll();
}
