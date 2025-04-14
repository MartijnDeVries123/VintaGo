package org.vfl.vintago.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.vfl.vintago.dto.CoordinateDTO;
import reactor.core.publisher.Mono;

@Service
public class GeocodingService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeocodingService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://maps.googleapis.com/maps/api")
                .build();
    }

    public Mono<CoordinateDTO> getCoordinates(String address) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geocode/json")
                        .queryParam("address", address)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        JsonNode root = objectMapper.readTree(response);
                        JsonNode location = root.path("results").get(0).path("geometry").path("location");
                        double lat = location.path("lat").asDouble();
                        double lng = location.path("lng").asDouble();
                        return new CoordinateDTO(lat, lng);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse geocoding response", e);
                    }
                });
    }
}
