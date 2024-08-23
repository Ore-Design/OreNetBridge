package design.ore.OreNetBridge.restlets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RESTletDiscountItem
{
	String id;
	RESTletDiscountValues values;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class RESTletDiscountValues
	{
		String itemid;
		String displayName;
		String description;
		String baseprice;
	}
}
