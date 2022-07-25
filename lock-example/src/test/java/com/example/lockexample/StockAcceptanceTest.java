package com.example.lockexample;

import com.example.lockexample.ui.StockRequest;
import com.example.lockexample.ui.StockResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 재고를_차감한다() {
        StockResponse 재고 = N개의_재고를_생성(1L).as(StockResponse.class);
        N개의_재고를_차감(재고.getId(), 1L);
    }

    @Test
    void 동시에_재고를_차감한다() throws InterruptedException {
        StockResponse 재고 =  N개의_재고를_생성(1L).as(StockResponse.class);

        int numberOfThreads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i=0; i<numberOfThreads; i++) {
            executorService.execute(() -> {
                N개의_재고를_차감(재고.getId(), 1L);
                latch.countDown();
            });
        }

        latch.await();
    }

    ExtractableResponse<Response> N개의_재고를_생성(Long stockCount) {
        StockRequest stockRequest = new StockRequest("돈까스", "T-1101", stockCount);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(stockRequest)
            .when().post("/stocks")
            .then().log().all()
            .extract();
    }

    ExtractableResponse<Response> N개의_재고를_차감(Long stockId, Long pickingCount) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("pickingCount", pickingCount)
            .when().put("/stocks/{stockId}/picking", stockId)
            .then().log().all()
            .extract();

    }
}
