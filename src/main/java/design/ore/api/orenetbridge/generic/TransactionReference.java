package design.ore.api.orenetbridge.generic;

import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReference
{
	String id, type, entity;
	@JsonAlias({"custbody_flore_notes", "custbody_so_flore_notes"})
	String notes;
	@JsonProperty("tranid")
	String name;
	Boolean wip;

	public void setWipFromStr(String str) { wip = str.toLowerCase().contains("t"); }

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TransactionReference)) return false;

		TransactionReference ref = (TransactionReference) obj;

		return ((id == null && ref.id == null) || id.equals(ref.id)) &&
				((type == null && ref.type == null) || type.equals(ref.type)) &&
				((entity == null && ref.entity == null) || entity.equals(ref.entity)) &&
				((name == null && ref.name == null) || name.equals(ref.name));
	}
}
