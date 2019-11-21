package SecretSanta.Santa.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@ToString
public class ParseExcel {

    private Map<String, String> excelMap = new HashMap<>();

    public Map<String, String> fillExcelMap(){


        return excelMap;
    }
}
