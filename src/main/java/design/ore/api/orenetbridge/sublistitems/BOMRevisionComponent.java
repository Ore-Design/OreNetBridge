package design.ore.api.orenetbridge.sublistitems;

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
public class BOMRevisionComponent
{
	public static final String STOCK_SOURCE = "STOCK";
	public static final String PHANTOM_SOURCE = "PHANTOM";
	
	String id;
	String refName;
	double quantity;
	String itemSource;
	
	public BOMRevisionComponent(String id, double quantity, String itemSource) { this.id = id; this.quantity = quantity; this.itemSource = itemSource; }
}
