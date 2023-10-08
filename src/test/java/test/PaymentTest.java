package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import data.DataHelper;
import data.DbHelper;
import page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldPaymentApprovedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("approved");
        val paymentPage = new OrderPage().goToPayment();
        paymentPage.payment(cardInfo);
        paymentPage.approved();
        assertEquals("APPROVED",new DbHelper().getPaymentStatus());
        assertEquals(4500000, new DbHelper().getPaymentAmount());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    void shouldPaymentDeclinedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("declined");
        val paymentPage = new OrderPage().goToPayment();
        paymentPage.payment(cardInfo);
        paymentPage.declined();
        assertEquals("DECLINED", new DbHelper().getPaymentStatus());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    void shouldGetNotificationInvalidCard() {
        val cardInfo = new DataHelper().getInvalidCardInfo("approved");
        val paymentPage = new OrderPage().goToPayment();
        paymentPage.payment(cardInfo);
        paymentPage.invalidCardNotification();
    }

    @Test
    void shouldGetNotificationWrongFormatCard() {
        val cardInfo = new DataHelper().getInvalidFormatCard("4444");
        val paymentPage = new OrderPage().goToPayment();
        paymentPage.payment(cardInfo);
        paymentPage.wrongFormatNotification();
    }

    @Test
    void shouldGetNotificationEmptyFields() {
        val paymentPage = new OrderPage().goToPayment();
        paymentPage.emptyFieldNotification();
    }
}