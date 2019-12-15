package org.mjjaenl.reactivetutorial.controller.v1;

import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_STREAM_URI_V1;

import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.service.ItemCappedReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_URI_V1;

@RestController
public class ItemStreamController {
    @Autowired
    private ItemCappedReactiveService itemCappedReactiveService;

    @GetMapping(value = ITEM_STREAM_URI_V1, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ItemCapped> getAllStream(@RequestParam Map<String,String> params) {
        return itemCappedReactiveService.findAll();
    }
}
