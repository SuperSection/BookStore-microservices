package com.supersection.bookstore.orders.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.supersection.bookstore.orders.AbstractIntegrationTest;
import com.supersection.bookstore.orders.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTests extends AbstractIntegrationTest {

    @Nested
    class CreateOrderTests {
        static final String apiEndpoint = "/api/orders";

        @Test
        void shouldCreateOrderSuccessfully() {
            var payload =
                    """
                    {
                        "customer": {
                            "name": "Soumo",
                            "email": "soumo@gmail.com",
                            "phone": "9876543210"
                        },
                        "deliveryAddress": {
                            "addressLine1": "Haltu",
                            "addressLine2": "Ballygunge",
                            "city": "Kolkata",
                            "state": "West Bengal",
                            "zipCode": "700001",
                            "country": "India"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "Product 1",
                                "price": 75.50,
                                "quantity": 1
                            }
                        ]
                    }
                    """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post(apiEndpoint)
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post(apiEndpoint)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
