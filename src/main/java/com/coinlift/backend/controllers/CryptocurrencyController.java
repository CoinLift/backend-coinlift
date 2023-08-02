package com.coinlift.backend.controllers;

import com.coinlift.backend.pojo.CryptoData;
import com.coinlift.backend.pojo.CryptoEvent;
import com.coinlift.backend.pojo.CryptoPercentData;
import com.coinlift.backend.pojo.CryptocurrencyNews;
import com.coinlift.backend.services.apis.CryptoDataService;
import com.coinlift.backend.services.apis.CryptoEventsService;
import com.coinlift.backend.services.apis.CryptocurrencyNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cryptocurrency")
@CrossOrigin("*")
@Tag(name = "Cryptocurrency Controller", description = "APIs related to cryptocurrency data, news, and events")
public class CryptocurrencyController {

    private final CryptoDataService dataService;

    private final CryptocurrencyNewsService newsService;

    private final CryptoEventsService eventsService;

    public CryptocurrencyController(CryptoDataService dataService, CryptocurrencyNewsService newsService, CryptoEventsService eventsService) {
        this.dataService = dataService;
        this.newsService = newsService;
        this.eventsService = eventsService;
    }

    /**
     * Get the latest percentage change data for cryptocurrencies.
     *
     * @return A ResponseEntity containing a list of CryptoPercentData objects and HttpStatus OK if successful.
     */
    @Operation(summary = "Get the latest percentage change data for cryptocurrencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crypto percent data fetched successfully", content = @Content(schema = @Schema(implementation = CryptoPercentData.class)))
    })
    @GetMapping("/percent-data")
    public ResponseEntity<List<CryptoPercentData>> getCryptoPercentData() {
        return ResponseEntity.ok(dataService.getCryptoPercentData());
    }

    /**
     * Get all cryptocurrency data.
     *
     * @return A ResponseEntity containing a list of CryptoData objects and HttpStatus OK if successful.
     */
    @Operation(summary = "Get all cryptocurrency data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All cryptocurrency data fetched successfully", content = @Content(schema = @Schema(implementation = CryptoData.class)))
    })
    @GetMapping("/all-data")
    public ResponseEntity<List<CryptoData>> getCryptoData() {
        return ResponseEntity.ok(dataService.getAllCryptoData());
    }

    /**
     * Get the latest cryptocurrency news articles.
     *
     * @return A ResponseEntity containing a list of CryptocurrencyNews objects and HttpStatus OK if successful.
     */
    @Operation(summary = "Get the latest cryptocurrency news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Latest cryptocurrency news articles fetched successfully", content = @Content(schema = @Schema(implementation = CryptocurrencyNews.class)))
    })
    @GetMapping("/news")
    public ResponseEntity<List<CryptocurrencyNews>> getCryptocurrencyNews() {
        return ResponseEntity.ok(newsService.getNewsArticles());
    }

    /**
     * Get the latest cryptocurrency events.
     *
     * @return A ResponseEntity containing a list of CryptoEvent objects and HttpStatus OK if successful.
     */
    @Operation(summary = "Get the latest cryptocurrency events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Latest cryptocurrency events fetched successfully", content = @Content(schema = @Schema(implementation = CryptoEvent.class)))
    })
    @GetMapping("/events")
    public ResponseEntity<List<CryptoEvent>> getCryptoEvent() {
        return ResponseEntity.ok(eventsService.getLatestCryptoEvents());
    }
}
