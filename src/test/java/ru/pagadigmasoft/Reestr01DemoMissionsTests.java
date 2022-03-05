package ru.pagadigmasoft;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    //Командируемый: Filters_StaffID == TestMan3
    @DisplayName("Bad: Поиск в динамичном списке")
    void filterDinamic() {
        loginBase();
        open("/Missions");

        $(byName("Filters_StaffID")).shouldBe(visible, Duration.ofSeconds(10));
        //+
        Selenide.executeJavaScript("$('select[name=Filters_StaffID]').val('TestMan3').trigger('change')");
        //Selenide.executeJavaScript("$('select[class=select2-search__field]').typeText('TestMan3').trigger('change')");

        //$("[name=Filters_StaffID]").setValue("TestMan3").pressEnter(); //не раб.
        //$("[name=Filters_StaffID]").$(".dropdown-wrapper").setValue("TestMan3").pressEnter(); //не раб.
        sleep(5000);
        $("#refresh_button").click(); // кнопка
        sleep(5000);
        $$("td").find(text("3")).shouldBe(visible); //проверка6 в выдаче только 1 запись, где id=3
    }

    @Test
    //Основное место работы:
    //<option value="3">Компания 3</option>
    @DisplayName("NEW!Поиск в value-списке(ок)")
    void filterValue() {
        loginBase();
        open("/Missions");
        $(byName("Filters_Firm")).shouldBe(visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("$('select[name=Filters_Firm]').val('3').trigger('change')");
        //шаблон:
        //$(byClassName("select2-container--default")).shouldBe(visible, Duration.ofSeconds(10))
        //Selenide.executeJavaScript("$('select[class=select2-container--default]').val('CA').trigger('change')");
        $("#refresh_button").click(); // кнопка
        sleep(3000);
        $$("td").find(text("3")).shouldBe(visible); //проверка
        sleep(3000);
    }

    @Test
    //Основное место работы:
    //<option value="3">Компания 3</option>
    @DisplayName("New!False(exp) Поиск в value-списке")
    void filterValueAssertFalse() {
        loginBase();
        open("/Missions");
        $(byName("Filters_Firm")).shouldBe(visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("$('select[name=Filters_Firm]').val('3').trigger('change')");
        $("#refresh_button").click(); // кнопка
        sleep(3000);
        assertFalse($($$("td").find(text("33"))).isDisplayed()); //заведомо неверная выдача: ожид.ошибка (верно)
        sleep(3000);
    }

    @Test
    @DisplayName("Календарь заполнить(ок)")
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

    @Test
    // Поле Место командировки
    @DisplayName("Поиск с простым Input(ок)")
    void filterText () {
        loginBase();
        open("/Missions");
        $("[name=Filters_MissionPlace]").setValue("Псков").pressEnter();
        $("#refresh_button").click(); // кнопка
        sleep(1000);
        $$("td").find(text("12")).shouldBe(visible); //проверка
    }

    @Test
    @DisplayName("Простой залогин(ок)")
    void loginTest() {
        open("/Auth");
        $("[name=login]").setValue(userName);
        $("[name=password]").setValue(userPassword);
        //$("[name=login]").setValue("Stranger");
        //$("[name=password]").setValue("66PojexoIEB0");
        $("[name=valid_auth]").click();
        $$(".home_welcome").find(text("Добро пожаловать")).shouldBe(visible); // контроль: главная стр
    }
// =============================
   void loginBase () {
       open("/Auth");
       sleep(1000);
       $("[name=login]").setValue("Stranger");
       $("[name=password]").setValue("66PojexoIEB0");
       sleep(1000);
       $("[name=valid_auth]").click();
       sleep(1000);
       $$(".home_welcome").find(text("Добро пожаловать")).shouldBe(visible); // контроль: главная стр
   }
}


