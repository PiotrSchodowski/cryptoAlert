package pl.schodowski.CryptoPriceAlert.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crypto")
@Data
@Builder
public class Crypto {

    @Id
    private String id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("current_price")
    private float price;

    @JsonProperty("market_cap")
    private long marketCap;

    @JsonProperty("total_volume")
    private float totalVolume;

}
