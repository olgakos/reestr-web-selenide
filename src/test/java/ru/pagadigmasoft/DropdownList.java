package ru.pagadigmasoft;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

//public class DropdownList {
class DropdownList {
    private SelenideElement dropdownList;

    private static final String ITEM_SELECTOR = "li.select2-results__option";
    private static final String INPUT_SELECTOR = "input.select2-search__field";

    public DropdownList(SelenideElement dropdownList) {
        this.dropdownList = dropdownList;
    }

    public void selectFirstElement() {
        selectByIndex(0);
    }

    public void searchElement(String query) {
        dropdownList.scrollIntoView(false).click();
        dropdownList.$$(ITEM_SELECTOR).shouldHave(sizeGreaterThan(0));
        dropdownList.$(INPUT_SELECTOR).setValue(query);
        dropdownList.$$(ITEM_SELECTOR).shouldHave(size(1));
        dropdownList.$(INPUT_SELECTOR).pressEnter();
    }

    public void searchElementNoWaitAfterClick(String query) {
        dropdownList.scrollIntoView(false).click();
        dropdownList.$(INPUT_SELECTOR).setValue(query);
        dropdownList.$$(ITEM_SELECTOR).shouldHave(size(1));
        dropdownList.$(INPUT_SELECTOR).pressEnter();
    }

    public void clearSelectedElement() {
        dropdownList.$("[ng-click='$select.clear($event)']").scrollIntoView(false).click();
    }

    public void selectByIndex(int index) {
        dropdownList.scrollIntoView(false).click();
        dropdownList.$$(ITEM_SELECTOR).shouldHave(sizeGreaterThan(0));
        dropdownList.$$(ITEM_SELECTOR).get(index).click();
    }

    /*
    public void searchElementWithLoadingBar(String query) {
        dropdownList.scrollIntoView(false).click();
        new LoadingBar().waitForLoad();
        dropdownList.$$(ITEM_SELECTOR).shouldHave(sizeGreaterThan(0));
        dropdownList.$(INPUT_SELECTOR).setValue(query);
        dropdownList.$$(ITEM_SELECTOR).shouldHave(size(1));
        dropdownList.$(INPUT_SELECTOR).pressEnter();
    }
*/
}