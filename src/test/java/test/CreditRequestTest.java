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


public class CreditRequestTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("https://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldCreditApprovedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("approved");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.approved();
        assertEquals("APPROVED", new DbHelper().getCreditRequestStatus());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    void shouldPaymentDeclinedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("declined");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.declined();
        assertEquals("DECLINED", new DbHelper().getCreditRequestStatus());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    void shouldGetNotificationInvalidCard() {
        val cardInfo = new DataHelper().getInvalidCardInfo("approved");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.invalidCardNotification();
    }

    @Test
    void shouldGetNotificationWrongFormatCard() {
        val cardInfo = new DataHelper().getInvalidFormatCard("4444");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.wrongFormatNotification();
    }

    @Test
    void shouldGetNotificationEmptyFields() {
        val creditPage = new OrderPage().goToCredit();
        creditPage.emptyFieldNotification();
    }

}