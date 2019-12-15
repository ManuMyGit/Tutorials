package org.mjjaenl.reactivetutorial.service.impl;

import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.repository.ItemCappedReactiveRepository;
import org.mjjaenl.reactivetutorial.service.ItemCappedReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ItemCappedReactiveServiceImpl implements ItemCappedReactiveService {
    @Autowired
    private ItemCappedReactiveRepository itemCappedReactiveRepository;

    @Override
    public Flux<ItemCapped> findAll() {
        return itemCappedReactiveRepository.findAllBy();
    }
}
