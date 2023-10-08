package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class OrderPage {
    private SelenideElement heading = $$(".heading").find(exactText("Путешествие дня"));
    private SelenideElement paymentButton = $$(".button__text").find(exactText("Купить"));
    private SelenideElement creditButton = $$(".button__text").find(exactText("Купить в кредит"));

    public OrderPage() {
        heading.shouldBe(Condition.visible);
    }

    public PaymentPage goToPayment() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage goToCredit() {
        creditButton.click();
        return new CreditPage();
    }
}