package data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    private Faker faker = new Faker();

    private String getCardNumber(String card) {
        if (card.equalsIgnoreCase("approved")) {
            return "4444 4444 4444 4441";
        } else if (card.equalsIgnoreCase("declined")) {
            return "4444 4444 4444 4442";
        }
        else return card;
    }

    private String generateMonth() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        String date = LocalDate.now().plusMonths(3).format(format);
        return date;
    }

    private String generateYear() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy");
        String date = LocalDate.now().plusYears(2).format(format);
        return date;
    }

    private String generateOwnerName() {
        String ownerName = faker.name().lastName().toUpperCase() + " " + faker.name().firstName().toUpperCase();
        return ownerName;
    }

    private String generateCvc() {
        return Integer.toString(faker.number().numberBetween(100, 999));
    }

    @Value
    public static class CardInfo {
        String CardNumber;
        String month;
        String year;
        String ownerName;
        String cvc;
    }

    public CardInfo getValidCardInfo(String card) {
        return new CardInfo(getCardNumber(card), generateMonth(), generateYear(), generateOwnerName(), generateCvc());
    }

    public CardInfo getInvalidCardInfo(String card) {
        return new CardInfo(getCardNumber(card), "13", "20", "Петров Петр", generateCvc());
    }
    public CardInfo getInvalidFormatCard (String card) {
        return new CardInfo(getCardNumber(card), "444", "4", "4444 @!", "1");
    }

}