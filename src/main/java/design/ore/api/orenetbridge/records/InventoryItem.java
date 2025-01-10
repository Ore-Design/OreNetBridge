package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.api.orenetbridge.abstractions.ValueStorageRecord;
import design.ore.api.orenetbridge.generic.NsID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem extends ValueStorageRecord
{
	@JsonProperty("internalId") String id;
	String itemId;
	String displayName;
	String description;
	String baseUnit;
	String externalId;
	@JsonProperty("custitem_ore3d_price") Double cost;
	@JsonProperty("custitem_ore3d_display") Boolean display;
	@JsonProperty("custitem_ore3d_nomargin") Boolean noMargin;
	@JsonProperty("custitem_ore3d_finishtype") NsID finishType;
	@JsonProperty("custitem_ore3d_build_material") NsID metalType;
	@JsonProperty("custitem_ore3d_sheetmetal_thickness") NsID metalThickness;
	@JsonProperty("custitem_ore3d_bomtype") NsID bomType;
	@JsonProperty("custitem_ore3d_is_hburner") Boolean hBurner;
	@JsonProperty("custitem_ore3d_burner_is_round") Boolean roundBurner;
	@JsonProperty("custitem_ore3d_burner_type") NsID burnerType;
	@JsonProperty("custitem_ore3d_burner_length") Double burnerLength;
	@JsonProperty("custitem_ore_3d_burner_width") Double burnerWidth;
}
