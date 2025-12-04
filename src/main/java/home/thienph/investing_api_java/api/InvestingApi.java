package home.thienph.investing_api_java.api;

import com.microsoft.playwright.*;
import home.thienph.investing_api_java.constants.BrowserEngine;
import home.thienph.investing_api_java.constants.Interval;
import home.thienph.investing_api_java.constants.Period;
import home.thienph.investing_api_java.constants.PointsCount;
import home.thienph.investing_api_java.data.ChartData;
import home.thienph.investing_api_java.exceptions.InvestingApiException;
import home.thienph.investing_api_java.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
public class InvestingApi {

    @Setter
    BrowserEngine browserEngine = BrowserEngine.CHROMIUM;
    @Setter
    BrowserType.LaunchOptions BrowserOption = new BrowserType.LaunchOptions();
    @Setter
    boolean useBrowserContext = false;

    Playwright playwright;
    Browser browser;

    boolean isInitialized = false;


    synchronized public void initialize() {
        if (!isInitialized) {
            playwright = Playwright.create();
            browser = getBrowser();
            isInitialized = true;
        }
    }

    synchronized public ChartData get(String paidId) {
        return get(paidId, Period.P1M, Interval.P1D, PointsCount.PC60);
    }

    synchronized public ChartData get(String paidId, Period period, Interval interval, PointsCount pointCount) {
        String urlPath = String.format("https://api.investing.com/api/financialdata/%s/historical/chart?period=%s&interval=%s&pointscount=%d",
                paidId, period.name(), interval.name(), pointCount.getCount());
        Page page = newPage();
        Response response = page.waitForResponse(res -> {
            String contentType = res.headers().get("content-type");
            return contentType != null && contentType.contains("application/json");
        }, () -> page.navigate(urlPath));
        String json = response.text();
        page.close();
        return JsonUtil.toObject(json, ChartData.class);
    }

    public int totalPageSize() {
        return browser.contexts().stream()
                .mapToInt(ctx -> ctx.pages().size())
                .sum();
    }

    synchronized public void close() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        isInitialized = false;
    }

    private Browser getBrowser() {
        if (playwright == null)
            throw new InvestingApiException("Playwright is not initialized");
        return switch (browserEngine) {
            case CHROMIUM -> playwright.chromium().launch(BrowserOption);
            case FIREFOX -> playwright.firefox().launch(BrowserOption);
            case WEBKIT -> playwright.webkit().launch(BrowserOption);
        };
    }

    private Page newPage() {
        if (browser == null)
            throw new InvestingApiException("Browser is not initialized");
        if (useBrowserContext)
            return browser.newContext().newPage();
        else
            return browser.newPage();
    }
}
