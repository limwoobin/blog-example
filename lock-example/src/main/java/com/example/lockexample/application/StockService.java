package com.example.lockexample.application;

import com.example.lockexample.domain.Stock;
import com.example.lockexample.domain.StockRepository;
import com.example.lockexample.ui.StockRequest;
import com.example.lockexample.ui.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional
    public StockResponse createStock(StockRequest stockRequest) {
        Stock stock = stockRequest.toStock();
        stockRepository.save(stock);
        return StockResponse.toResponse(stock);
    }

    @Transactional
    public void decrease(Long stockId, Long pickingCount) {
        Stock stock = stockRepository.findById(stockId)
            .orElseThrow(IllegalStateException::new);

        stock.decrease(pickingCount);
    }
}
