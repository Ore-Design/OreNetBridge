package design.ore.OreNetBridge.fuseeditor;

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
@NoArgsConstructor
@AllArgsConstructor
public class FuseEditorProduct
{
	String internalId;
	String productId;
	String name;
	Double length;
	Double width;
	Double height;
	String type;
}
