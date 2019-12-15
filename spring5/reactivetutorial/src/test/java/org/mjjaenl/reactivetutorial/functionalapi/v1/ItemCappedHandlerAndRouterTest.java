package org.mjjaenl.reactivetutorial.functionalapi.v1;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.initialize.InitializeData;
import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.utils.ItemConstants;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemCappedHandlerAndRouterTest {
    @Autowired
    private InitializeData initializeData;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        System.out.println("\n\nINIT settingUp ******");
        initializeData.initializeCappedData();
        System.out.println("END settingUp ******");
    }

    @Test
    public void test001GetAllStream() {
        System.out.println("\n\nTEST test001GetAllStream ******");
        Flux<ItemCapped> items = webTestClient.get().uri(ItemConstants.ITEM_STREAM_URI_V1_FUNCTIONAL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
                .returnResult(ItemCapped.class)
                .getResponseBody()
                .take(5);
        StepVerifier.create(items.log())
                .expectNextCount(5)
                .thenCancel()
                .verify();
    }
}
