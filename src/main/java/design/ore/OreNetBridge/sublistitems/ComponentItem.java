package design.ore.OreNetBridge.sublistitems;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.OreNetBridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(value = "tableDisplay", ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class ComponentItem
{
	public ComponentItem(NsID id, double quantity)
	{
		this.quantity = new BigDecimal(quantity).setScale(5, RoundingMode.HALF_UP).doubleValue();
		this.item = id;
	}
	
	@Getter @Setter String itemId;
	@Getter @Setter double quantity;
	@Getter @Setter NsID item;
}
