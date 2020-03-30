import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.val;

import java.lang.annotation.Annotation;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    @Getter
    protected SelenideElement buyButton = $(byText("Купить"));
    protected SelenideElement creditButton = $(byText("Купить в кредит"));
    protected SelenideElement numberCard= $("[placeholder='0000 0000 0000 0000']");
    protected SelenideElement monthCard = $("[placeholder='08']");
    protected SelenideElement yearCard = $("[placeholder='22']");
    protected SelenideElement ownerCard = $(byText("Владелец")).parent().$(byCssSelector(".input__control"));
    protected SelenideElement cvcCard = $("[placeholder='999']");
    protected SelenideElement continueButton = $(byText("Продолжить"));
    protected SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    protected SelenideElement notificationError = $(byCssSelector(".notification_status_error"));
    protected SelenideElement invalidFormat = $ (".input_sub");

    public BuyPage buyPage() {
        buyButton.click();
        return new BuyPage();
    }

    public CreditPage creditPage() {
        creditButton.click();
        return new CreditPage();
    }

    public void continueButton() {
        continueButton.click();
    }

    public void validData (Card card) {
        numberCard.setValue(card.getNumber());
        monthCard.setValue(card.getMonth());
        yearCard.setValue(card.getYear());
        ownerCard.setValue(card.getOwner());
        cvcCard.setValue(card.getCvc());
        continueButton.click();
    }

    public void checkOperationSuccess() {
        notificationSuccess.waitUntil(Condition.visible, 15000);
    }

    public void checkOperationError() {
        notificationError.waitUntil(Condition.visible, 15000);
    }

    public void checkMonthFormatError() {
        invalidFormat.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEarlyYearError() {
        yearCard.shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void checkShortCvcError() {
        cvcCard.shouldHave(Condition.exactText("Неверный формат"));
    }


}





