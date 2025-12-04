package home.thienph.investing_api_java.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PointsCount {
    PC60(0, 60),
    PC70(1, 70),
    PC90(2, 90),
    PC110(3, 110),
    PC120(4, 120),
    PC140(5, 140),
    PC160(6, 160);
    private final int id;
    private final int count;
}
