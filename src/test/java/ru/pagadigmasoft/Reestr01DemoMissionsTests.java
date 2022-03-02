package ru.pagadigmasoft;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Кадры:Командировки")
public class Reestr01DemoMissionsTests {
    String userName = "Stranger";
    String userPassword = "66PojexoIEB0";

    @BeforeEach
    void preconditionBrowser() {
        baseUrl = "http://62.152.34.179:8080";
        browserSize = "1920x1080";
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("РАБОТАЕТ: Залогин")
    void loginTest() {
        open("/Auth");
        $("[name=login]").setValue(userName);
        $("[name=password]").setValue(userPassword);
        //$("[name=login]").setValue("Stranger");
        //$("[name=password]").setValue("66PojexoIEB0");
        $("[name=valid_auth]").click();
        $$(".home_welcome").find(text("Добро пожаловать")).shouldBe(visible); // контроль: главная стр
    }

    @Test
    @DisplayName("РАБОТАЕТ: Календарь")
    void filterCalendar() {
        loginBase();
        open("/Missions");
        $$("div").find(text("Командируемый")).shouldBe(visible); // контрольный якорь: на нужной стр.
        $("[name=Filters_DateStart]").setValue("01.01.2019").pressEnter();
        $("[name=Filters_DateEnd]").setValue("31.03.2022").pressEnter();
        $("#refresh_button").click();
        sleep(5000);
        //проверка
        $$("td").find(text("12")).shouldBe(visible);
    }

        @Test // Поле Место командировки
        @DisplayName("РАБОТАЕТ: Поиск с простым текстом")
        void filterText () {
            loginBase();
            open("/Missions");
            $$("div").find(text("Командируемый")).shouldBe(visible); // контрольный якорь: на нужной стр.
            $("[name=Filters_MissionPlace]").setValue("Псков").pressEnter();
            $("#refresh_button").click(); // кнопка
            sleep(1000);
            $$("td").find(text("12")).shouldBe(visible); //проверка
        }

    @Test //Командируемый:
    @DisplayName("Не работает: Поиск в динамичном списке")
    void filterDinamic() {
        loginBase();
        open("/Missions");
        $$("div").find(text("Командируемый")).shouldBe(visible); // контрольный якорь: на нужной стр.


        $("[name=Filters_StaffID]").setValue("TestMan2").pressEnter();
        //$("[name=Filters_StaffID]").$(".dropdown-wrapper").setValue("TestMan2").pressEnter();


        $("#refresh_button").click(); // кнопка
        sleep(1000);
        $$("td").find(text("12")).shouldBe(visible); //проверка
    }

    @Test //Основное место работы:
    //<option value="2">Компания 2</option>
    @DisplayName("Не работает: Поиск в value-списке")
    void filterValue() {
        loginBase();
        open("/Missions");
        $$("div").find(text("Командируемый")).shouldBe(visible); // контрольный якорь: на нужной стр.

        $("[name=Filters_Firm]").setValue("Компания 2").pressEnter();
        // не работает $("$('select[name=Filters_Firm]').val('2').trigger('change')").setValue("Компания 2").pressEnter();
        //$$("$('select[name=Filters_Firm]')".valueOf(2).trigger(change).pressEnter();
        //$("[name=Filters_Firm]").$(".dropdown-wrapper").setValue("Компания 2").pressEnter();
        //$("[name=Filters_Firm] .select2-search__field").setValue("Компания 2").pressEnter();

        $("#refresh_button").click(); // кнопка
        sleep(1000);
        $$("td").find(text("12")).shouldBe(visible); //проверка
    }

    // =============================
   void loginBase () {
       open("/Auth");
       $("[name=login]").setValue("Stranger");
       $("[name=password]").setValue("66PojexoIEB0");
       $("[name=valid_auth]").click();
       $$(".home_welcome").find(text("Добро пожаловать")).shouldBe(visible); // контроль: главная стр
   }


        //динамично формирующйися список
        //$("[name=]").setValue("").pressEnter();

        //Календарь
        //кнопка
        //$("[onclick=empty_all_filters]").click();

        //$("body").shouldHave(text(expectedText));
        //$("#2324").shouldHave(Condition.text("2324"));
        //$$("td").find(text("2324")).shouldBe(visible);
        //$("tr").shouldHave(Condition.text("2324"));
        //$(By.partialLinkText("2324")).should(Condition.exist);
        //$(withText("2324")).should(Condition.exist);

        //
        //$(By.linkText("Очистить фильтры")).click();


    /*
    @Test
    @DisplayName("Фильтр-1")
        void missionsFilters1 (){}

    void loginTest1 (String userName, String userPassword) {
        open("/Auth");
        $("[name=login]").setValue(userName);
        $("[name=password]").setValue(userPassword);
        $("[name=valid_auth]").click();
        $(".home_welcome").shouldHave(Condition.text("Реестр"));
    }

     */
}


