# Investing.com Unofficial API for JAVA
### Introduction
Unofficial APIs for Investing.com website.  
`investing-com-api-v2` for NodeJS project, [see here](https://github.com/PhamHuyThien/investing-com-api-v2)

### Install
 - step 1: compile with `mvn clean install`
 - step 2: add to your `pom.xml`
```xml
<dependency>
    <groupId>home.thienph</groupId>
    <artifactId>investing-api-java</artifactId>
    <version>1.0.0</version>
</dependency>
```
- or install the jar module into your project, download from [git releases](https://github.com/PhamHuyThien/investing-api-java/releases/new)
### Example
```java
InvestingApi investingApi = new InvestingApi();
investingApi.setBrowserEngine(BrowserEngine.CHROMIUM); // optional: default CHROMIUM
investingApi.setBrowserOption(new BrowserType.LaunchOptions().setHeadless(true)); // optional: default empty options
investingApi.setUseBrowserContext(true); // optional: default false
investingApi.init(); // call required
ChartData chartData = investingApi.get(
        "1", //provide a valid investing.com pairId. (Required)
        Period.P1M, //Period of time, window size. Default P1M (1 month). Valid values: P1D, P1W, P1M, P3M, P6M, P1Y, P5Y, MAX.
        Interval.PT1M, //Interval between results. Default P1D (1 day). Valid values: PT1M, PT5M, PT15M, PT30M, PT1H, PT5H, P1D, P1W, P1M.
        PointsCount.PC120 //number of total results. Valid values seems to be 60, 70 90, 110, 120, 140 or 160.
);
System.out.println(chartData);
investingApi.close();
```
### How to get pairId?

- step 1: visit [this link](https://www.investing.com/) and search for the pair you want to get pairId.
- step 2: See image instructions below
![How to get pairId](https://i.imgur.com/2FFngFy.png)

### Important Note!!!

- Playwright library is not thread-safe.
- Your project only instantiates a single InvestingApi class, otherwise errors may occur.
- The `InvestingApi.get(...)` function is synchronized, even if you use multithreading, you still have to wait for each other.

### Author
- [Pham Huy Thien](https://github.com/PhamHuyThien/)
