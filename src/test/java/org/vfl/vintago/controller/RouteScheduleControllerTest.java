package org.vfl.vintago.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.dto.ScheduleRequestDTO;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteScheduleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateSchedule_withValidInput() {
        ScheduleRequestDTO request = new ScheduleRequestDTO();
        request.setSimulationType("sim20");
        request.setDays("day");
        request.setSolver("ortools");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ScheduleRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RouteDTO[]> response = restTemplate.postForEntity(
                "/api/routes-schedule/create-schedule",
                entity,
                RouteDTO[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RouteDTO[] solutions = response.getBody();
        assertThat(solutions).isNotNull();
        for (RouteDTO solution: solutions) {
            assertThat(solution.getAddresses()).isNotEmpty();
        }
    }
}