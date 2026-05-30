package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Used in TC8, TC9, TC18, TC19, TC20, TC21
 */
public class ProductsPage extends BasePage {

    private final By productsList       = By.cssSelector(".features_items");
    private final By searchBox          = By.id("search_product");
    private final By searchButton       = By.id("submit_search");
    private final By searchedProducts   = By.cssSelector(".features_items");
    private final By viewProductBtns    = By.cssSelector("a[href*='/product_details/']");
    private final By brandsSidebar      = By.cssSelector(".brands_products");
    private final By firstAddToCartBtn  = By.cssSelector(".productinfo .btn");
    private final By continueShoppingBtn = By.cssSelector("button.close-modal");
    private final By viewCartBtn        = By.cssSelector(".modal-body a[href='/view_cart']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/products");
    }

    public boolean isProductsPageVisible() {
        return helper.isDisplayed(productsList);
    }

    public int getProductCount() {
        return driver.findElements(productsList).size();
    }

    public void clickViewProduct(int index) {
        List<WebElement> viewBtns = driver.findElements(viewProductBtns);
        if (index < viewBtns.size()) {
            helper.scrollToElement(viewBtns.get(index));
            viewBtns.get(index).click();
        }
    }

    // Search

    public void searchForProduct(String keyword) {
        helper.type(searchBox, keyword);
        helper.click(searchButton);
    }

    public int getSearchResultCount() {
        return driver.findElements(searchedProducts).size();
    }

    public boolean isSearchResultsVisible() {
        return helper.isDisplayed(searchedProducts);
    }

    // Add to Cart

    public void addFirstProductToCart() {
        List<WebElement> addBtns = driver.findElements(firstAddToCartBtn);
        if (!addBtns.isEmpty()) {
            helper.scrollToElement(addBtns.get(0));
            helper.jsClick(addBtns.get(0));
        }
    }

    public void addProductToCart(int index) {
        // Re-fetch the list every time to avoid stale element
        List<WebElement> items = driver.findElements(productsList);
        if (index < items.size()) {
            WebElement item = items.get(index);
            helper.scrollToElement(item);
            // Hover over product to reveal Add to Cart button
            new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(item)
                    .perform();
            // Wait for Add to Cart button to be visible after hover
            WebElement addBtn = helper.waitForVisible(By.cssSelector(".productinfo .btn")
            );
            helper.jsClick(addBtn);
        }
    }

    public void clickContinueShopping() {
        helper.click(continueShoppingBtn);
    }

    public void clickViewCart() {
        helper.waitForVisible(By.cssSelector(".modal-body"));
        helper.click(viewCartBtn);
    }

    // Brands Sidebar

    public boolean isBrandsSidebarVisible() {
        return helper.isDisplayed(brandsSidebar);
    }

}
