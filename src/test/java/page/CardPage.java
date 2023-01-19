package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import userdata.UserData;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class CardPage {

    private SelenideElement sumAmount = $("[data-test-id='amount'] input");
    private SelenideElement sumFrom = $("[data-test-id='from'] input");
    private SelenideElement buttonFrom = $("[data-test-id='action-transfer']");


    public DashboardPage transferMoney(int amount, UserData.CardsInfo from) {
        sumAmount.setValue(valueOf(amount));
        sumFrom.setValue(String.valueOf(from));
        buttonFrom.click();
        return new DashboardPage();
    }
    public void getError() {
        $(byText("Сумма превышает доступный лимит!")).shouldBe(Condition.visible);
    }
}