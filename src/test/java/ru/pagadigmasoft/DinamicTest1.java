package ru.pagadigmasoft;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Кадры:Командировки")
public class DinamicTest1 //extends DropdowenList

{
    String userName = "Stranger";
    String userPassword = "66PojexoIEB0";
    @BeforeEach
    void preconditionBrowser() {
        baseUrl = "http://62.152.34.179:8080";
        browserSize = "1920x1080";
    }

//поле: Filters_StaffID (Командируемый)
//тестовые данные: TestMan3
//проверка: в выдаче 1 запись, где id=
// варинты тестов, не работает ни один (

    @Test
    void filterDinamic_var1_DropdownList() {
        loginBase();
        open("/Missions");
        //new DropdownList($("[name='Filters_StaffID']").sibling(0).$(".select2-container")).selectByIndex(0);
        //new DropdownList($("[name='Filters_StaffID']").sibling(0).$(".select2-container")).searchElement("dtc");
        new DropdownList($("[name='Filters_StaffID']").sibling(0).$(".select2-container")).searchElement("TestMan3");

        $("#refresh_button").click(); // кнопка искать
        sleep(5000);
        $$("td").find(text("3")).shouldBe(visible);
    }

    @Test
    void filterDinamic_var2_JS() {
        loginBase();
        open("/Missions");

        $(byName("Filters_StaffID")).shouldBe(visible, Duration.ofSeconds(10));
        //+
        Selenide.executeJavaScript("$('select[name=Filters_StaffID]').val('TestMan3').trigger('change')");
        //Selenide.executeJavaScript("$('select[class=select2-search__field]').typeText('TestMan3').trigger('change')");

        $("#refresh_button").click(); // кнопка искать
        sleep(5000);
        $$("td").find(text("3")).shouldBe(visible);
    }


    @Test
    void filterDinamic_var3_setValue() {
        loginBase();
        open("/Missions");

        $("[name=Filters_StaffID]").setValue("TestMan3").pressEnter(); //не раб.
        $("[name=Filters_StaffID]").$(".dropdown-wrapper").setValue("TestMan3").pressEnter(); //не раб.

        $("#refresh_button").click(); // кнопка искать
        sleep(5000);
        $$("td").find(text("3")).shouldBe(visible);
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


