
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userdata.UserData;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static userdata.UserData.getFirstCardInfo;
import static userdata.UserData.getSecondCardInfo;


public class TransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = UserData.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = UserData.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void TransferFirstToSecond() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance1();
        var balanceSecondBefore = dashboardPage.getCardBalance2();
        int amount = 2500;
        var verification = dashboardPage.personSecondCard();
        verification.transferMoney(amount, getFirstCardInfo());
        var balanceFirstAfter = balanceFirstBefore - amount;
        var balanceSecondAfter = balanceSecondBefore + amount;

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance1());
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance2());
    }

    @Test
    void TransferSecondToFirst() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance1();
        var balanceSecondBefore = dashboardPage.getCardBalance2();
        int amount = 5000;
        var verification = dashboardPage.personFirstCard();
        verification.transferMoney(amount, getSecondCardInfo());
        var balanceFirstAfter = balanceFirstBefore + amount;
        var balanceSecondAfter = balanceSecondBefore - amount;

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance1());
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance2());
    }

    @Test
    void TransferOverAmount() {
        var dashboardPage = new DashboardPage();

        int amount = 40000;
        var verification = dashboardPage.personFirstCard();
        verification.transferMoney(amount, getSecondCardInfo());
        verification.getError();

    }


}
