package models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorInputRequest{


	@Builder.Default
	private String swaggerUrl = "http://petstore.swagger.io/v2/swagger.json";

	@Builder.Default
	private boolean usingFlattenSpec = true;

}