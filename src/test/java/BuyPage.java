import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class BuyPage extends StartPage {


    public BuyPage() {

        $(withText("Оплата по карте")).shouldBe(Condition.visible);
    }
}

