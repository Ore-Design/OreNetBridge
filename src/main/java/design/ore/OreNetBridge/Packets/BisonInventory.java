package design.ore.OreNetBridge.Packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BisonInventory
{
	@Getter String id;
	@JsonProperty("custitem_bison_inventory_length") String length;
	@JsonProperty("custitem_bison_inventory_width") String width;
	@JsonProperty("custitem_bison_inventory_height") String height;

	public double getLength()
	{
		try { return Double.parseDouble(length); }
		catch(NumberFormatException | NullPointerException e) { return 0; }
	}
	public double getWidth()
	{
		try { return Double.parseDouble(width); }
		catch(NumberFormatException | NullPointerException e) { return 0; }
	}
	public double getHeight()
	{
		try { return Double.parseDouble(height); }
		catch(NumberFormatException | NullPointerException e) { return 0; }
	}
}
