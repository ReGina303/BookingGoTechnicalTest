package com.example.demo.controllers;

import com.example.demo.BookingGoAPI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingGoAPI.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
public class OfferControllerIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private HttpEntity<String> httpEntity;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        httpEntity = new HttpEntity<String>(headers);
    }

    @Test
    public void testGetAllOffers() {
        ResponseEntity<String> response = template.exchange("/offers", HttpMethod.GET, httpEntity, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
