package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private SelenideElement heading = $$(".heading").find(exactText("Оплата по карте"));
    private SelenideElement cardNumber = $(".input [placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(".input [placeholder='08']");
    private SelenideElement year = $(".input [placeholder='22']");
    private SelenideElement fieldCardOwner = $$(".input__top").find(text("Владелец")).parent();
    private SelenideElement cardOwner = fieldCardOwner.$(".input__control");
    private SelenideElement cvc = $(".input [placeholder='999']");
    private SelenideElement proceedButton = $(".form-field button");
    private SelenideElement approvedNotification = $(".notification_status_ok");
    private SelenideElement declinedNotification = $(".notification_status_error");
    private SelenideElement fieldCard = $$(".input__top").find(text("Номер карты")).parent();
    private SelenideElement fieldMonth = $$(".input__top").find(text("Месяц")).parent();
    private SelenideElement fieldYear = $$(".input__top").find(text("Год")).parent();
    private SelenideElement fieldCvc = $$(".input__top").find(text("CVC/CVV")).parent();

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void payment(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        cardOwner.setValue(info.getOwnerName());
        cvc.setValue(info.getCvc());
        proceedButton.click();
    }
    public void approved() {
        approvedNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void declined() {
        declinedNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void invalidCardNotification() {
        fieldMonth.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверно указан срок действия карты"));
        fieldYear.$(".input__sub").shouldBe(visible).shouldHave(ownText("Истёк срок действия карты"));
        fieldCardOwner.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
    }

    public void wrongFormatNotification() {
        fieldCard.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
        fieldMonth.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
        fieldYear.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
        fieldCardOwner.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
        fieldCvc.$(".input__sub").shouldBe(visible).shouldHave(ownText("Неверный формат"));
    }

    public void emptyFieldNotification() {
        proceedButton.click();
        fieldCard.$(".input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
        fieldMonth.$(".input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
        fieldYear.$(".input__sub").shouldBe(visible).shouldHave(ownText("Поле обязательно для заполнения"));
        fieldCardOwner.$(".input__sub").shouldBe(visible).shouldHave(ownText("Поле обязательно для заполнения"));
        fieldCvc.$(".input__sub").shouldBe(visible).shouldHave(ownText("Поле обязательно для заполнения"));
    }
}