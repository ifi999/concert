package com.hhp.concert.controller.payment;

import com.hhp.concert.controller.payment.dto.PayReservationRequest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PaymentControllerTest {

    @Test
    void 결제한다() {
        // given
        final long 사용자_ID = 123L;
        final long 예약_ID = 456L;
        final long 결제_금액 = 30_000;
        final PayReservationRequest 결제_요청 = new PayReservationRequest(예약_ID, 사용자_ID, 결제_금액);

        // when
        final JsonPath 결제예약_응답 =
            given()
                .log().all()
                .body(결제_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/payments")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 응답_결제_ID = 결제예약_응답.getLong("paymentId");
        final long 응답_예약_ID = 결제예약_응답.getLong("reservationId");
        final int 응답_결제_금액 = 결제예약_응답.getInt("paymentAmount");
        final String 응답_결제_시간 = 결제예약_응답.getString("paymentTime");

        assertThat(응답_결제_ID).isEqualTo(123L);
        assertThat(응답_예약_ID).isEqualTo(456L);
        assertThat(응답_결제_금액).isEqualTo(30_000);
        assertThat(응답_결제_시간).isEqualTo("2024-07-01T13:00:00");
    }

}
