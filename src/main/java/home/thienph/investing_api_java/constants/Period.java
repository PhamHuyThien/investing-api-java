package home.thienph.investing_api_java.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Period {
    P1D(0, "1d"),
    P1W(1, "1w"),
    P1M(2, "1m"),
    P3M(3, "3m"),
    P6M(4, "6m"),
    P1Y(5, "1y"),
    P2Y(6, "2y"),
    P5Y(7, "5y"),
    MAX(8, "max");
    private final int id;
    private final String date;
}
