package home.thienph.investing_api_java.test;

import com.microsoft.playwright.BrowserType;
import home.thienph.investing_api_java.api.InvestingApi;
import home.thienph.investing_api_java.constants.BrowserEngine;
import home.thienph.investing_api_java.constants.Interval;
import home.thienph.investing_api_java.constants.Period;
import home.thienph.investing_api_java.constants.PointsCount;
import home.thienph.investing_api_java.data.ChartData;
import home.thienph.investing_api_java.utils.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvestingApiTest {
    InvestingApi investingApi;

    @BeforeAll
    void initialize() {
        log.info("welcome to investing-api-java - code by PhamHuyThien <3");
        investingApi = new InvestingApi();
        investingApi.setBrowserEngine(BrowserEngine.CHROMIUM); // optional: default CHROMIUM
        investingApi.setBrowserOption(new BrowserType.LaunchOptions().setHeadless(false)); // optional: default empty options
        investingApi.setUseBrowserContext(true); // optional: default false
        investingApi.initialize(); // call required
    }

    @AfterAll
    void destroy() {
        investingApi.close();
        log.info("goodbye to investing-api-java - code by PhamHuyThien <3");
    }


    @Test
    void testSingleThread() {
        ChartData chartData = investingApi.get("1", Period.P1M, Interval.PT1M, PointsCount.PC60);
        log.info("testSingleThread / finished 1: {}", JsonUtil.toJson(chartData));
        Assertions.assertAll(
                "testSingleThread / Validate chartData",
                () -> Assertions.assertNotNull(
                        chartData,
                        "chartData bị null"
                ),
                () -> Assertions.assertNull(
                        chartData.getErrors(),
                        "chartData.getErrors() không null"
                ),
                () -> Assertions.assertNotNull(
                        chartData.getData(),
                        "chartData.getData() bị null"
                ),
                () -> Assertions.assertNotEquals(
                        0,
                        chartData.getData().size(),
                        "chartData.getData() rỗng"
                )
        );
    }

    @Test
    @SneakyThrows
    public void testMultiThread() {
        List<ChartData> chartDataResult = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    log.info("testMultiThread / running {}: ...", finalI);
                    ChartData chartData = investingApi.get(finalI + "", Period.P1M, Interval.PT1M, PointsCount.PC60);
                    log.info("testMultiThread / finished {}: {}", finalI, JsonUtil.toJson(chartData));
                    chartDataResult.add(chartData);
                } catch (Exception e) {
                    log.error("testMultiThread / error: {}", e.getMessage(), e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        executor.shutdownNow();

        Assertions.assertAll(
                "testMultiThread / Validate chartData",
                () -> Assertions.assertTrue(
                        chartDataResult.stream().allMatch(Objects::nonNull),
                        "chartDataResult chứa phần tử null"
                ),
                () -> Assertions.assertTrue(
                        chartDataResult.stream().allMatch(chartData -> chartData.getErrors() == null),
                        "chartData.getErrors() có phần tử null"
                ),
                () -> Assertions.assertTrue(
                        chartDataResult.stream().allMatch(chartData -> chartData.getData() != null),
                        "chartData.getData() null"
                ),
                () -> Assertions.assertTrue(
                        chartDataResult.stream().noneMatch(chartData -> chartData.getData().isEmpty()),
                        "chartData.getData() rỗng"
                )
        );
    }
}
