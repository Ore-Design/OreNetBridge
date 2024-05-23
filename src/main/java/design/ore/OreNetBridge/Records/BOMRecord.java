package design.ore.OreNetBridge.Records;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.OreNetBridge.Generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class BOMRecord
{
	public BOMRecord(String name, List<NsID> restrictedAssemblies, BOMRevision initialRevision)
	{
		this.name = name;
		this.availableForAllAssemblies = restrictedAssemblies.size() == 0;
		this.restrictToAssemblies = restrictedAssemblies;
		this.bomRevision = new ArrayList<>();
		this.bomRevision.add(initialRevision);
	}
	
	@Getter @Setter String id;
	@Getter @Setter String name;
	@Getter @Setter boolean availableForAllAssemblies;
	@Getter @Setter List<BOMRevision> bomRevision;
	@Getter @Setter List<NsID> restrictToAssemblies;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy h:mm a")
	@Getter @Setter LocalDateTime createdDate;
}
