package home.thienph.investing_api_java.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChartData {
    List<BigDecimal[]> data;
    Object events;
    @JsonProperty("@errors")
    List<String> errors;
}
