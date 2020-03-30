import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardPaymentTest {

    @AfterEach
    void cleanTables() throws SQLException {
        SqlHelper.cleanTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure",new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    Card cardApproved = new Card();
    Card cardDeclined = new Card();

    void setCardApproved () {
        cardApproved.setCardNumber(DataGeneration.approvedCardNumber());
        cardApproved.setMonth(DataGeneration.validMonth());
        cardApproved.setYear(DataGeneration.validYear());
        cardApproved.setOwner(DataGeneration.name());
        cardApproved.setCvc(DataGeneration.validCvc());

    }

    void setCardDeclined () {
        cardDeclined.setCardNumber(DataGeneration.declinedCardNumber());
        cardDeclined.setMonth(DataGeneration.validMonth());
        cardDeclined.setYear(DataGeneration.validYear());
        cardDeclined.setOwner(DataGeneration.name());
        cardDeclined.setCvc(DataGeneration.validCvc());
    }

    public StartPage openStartPage() {
        open("http://localhost:8080/");
        val startPage = new StartPage();
        return  startPage;
    }

    public BuyPage openBuyPageAndFill() {
        val buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        return buyPage;
    }

    public CreditPage openCreditPageAndFill() {
        val creditPage = openStartPage().creditPage();
        openStartPage().validData(cardApproved);
        return creditPage;
    }


    @DisplayName("1.Успешная оплата по карте **** 4441")
    @Test
    void shouldBuySuccess () throws SQLException {
        setCardApproved();
        openBuyPageAndFill().checkOperationSuccess();
        assertEquals("APPROVED", SqlHelper.selectBuyStatus() );
    }

    @DisplayName("2.Успешная оплата в кредит")
    @Test
    void shouldBuyInCreditSuccess () throws SQLException {
        setCardApproved();
        openCreditPageAndFill().checkOperationSuccess();
        assertEquals(SqlHelper.selectCreditStatus(), "APPROVED");
    }

    @DisplayName("3.Неуспешная оплата по невалидной карте")
    @Test
    void shouldNotValidBuy() throws SQLException {
        setCardDeclined();
        openBuyPageAndFill().checkOperationError();
        assertEquals(SqlHelper.selectBuyStatus(), "DECLINED");
    }

    @DisplayName("4.Неуспешная оплата в кредит по невалидной карте")
    @Test
    void shouldNotValidBuyCredit() throws SQLException {
        setCardDeclined();
        openCreditPageAndFill().checkOperationError();
        assertEquals(SqlHelper.selectCreditStatus(), "DECLINED");
    }

    @DisplayName("5.Ввести номер несуществующей карты")
    @Test
    void shouldMessageErrorCardError () {
        setCardApproved();
        cardApproved.setCardNumber("0000 0000 0000 0000");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationError();
    }

    @DisplayName("BUG 6.Ввести несуществующий номер месяца")
    @Test
    void shouldMessageErrorMonthError () {
        setCardApproved();
        cardApproved.setMonth("00");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkMonthFormatError();
    }

    @DisplayName("7.Ввести в поле Год прошедший год")
    @Test
    void shouldMessageErrorYearError () {
        setCardApproved();
        cardApproved.setYear(DataGeneration.setEarlyYear());
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkEarlyYearError();
    }

    @DisplayName("8.Ввести в поле владелец данные латинскими буквами")
    @Test
    void shouldBuySuccessEnglishOwner () {
        setCardApproved();
        cardApproved.setOwner("Ivan Ivanov");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationSuccess();
    }

    @DisplayName("BUG 9.Ввести в поле Владелец даннык с цифрами и буквами")
    @Test
    void shouldMessageErrorOwnerError () {
        setCardApproved();
        cardApproved.setOwner("В676768т");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationError();
    }

    @DisplayName("10.Ввести не полностью данные CVC")
    @Test
    void shouldMessageErrorCVCError () {
        setCardApproved();
        cardApproved.setCvc("12");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkShortCvcError();
    }
}
