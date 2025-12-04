package home.thienph.investing_api_java.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Interval {
    PT1M(0, "1m"),
    PT5M(1, "5m"),
    PT15M(2, "15m"),
    PT30M(3, "30m"),
    PT1H(4, "1h"),
    PT5H(5, "5h"),
    P1D(6, "1d"),
    P1W(7, "1w"),
    P1M(8, "1m");
    private final int id;
    private final String date;
}
