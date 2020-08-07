package haydende.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;

    @JsonProperty("customer_url")
    private String customerUrl;
}
