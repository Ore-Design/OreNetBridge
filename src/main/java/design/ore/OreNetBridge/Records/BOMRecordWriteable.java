package design.ore.OreNetBridge.Records;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import design.ore.OreNetBridge.Generic.NsID;
import design.ore.OreNetBridge.Generic.NsItemList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class BOMRecordWriteable
{
	public BOMRecordWriteable(BOMRecord record)
	{
		this.name = record.name;
		this.id = record.id;
		this.availableForAllAssemblies = record.availableForAllAssemblies;
		this.restrictToAssemblies = new NsItemList<NsID>(record.restrictToAssemblies);
		this.createdDate = record.createdDate;
		bomRevision = new NsItemList<>();
		for(BOMRevision r : record.bomRevision) { bomRevision.addItem(new NsID(r.getId())); }
	}
	
	public BOMRecordWriteable(String name, List<NsID> restrictedAssemblies)
	{
		this.name = name;
		this.availableForAllAssemblies = restrictedAssemblies.size() == 0;
		this.restrictToAssemblies = new NsItemList<>(restrictedAssemblies);
	}
	
	public BOMRecordWriteable(String name, NsID assembly/*, boolean shouldBeMasterRecord*/)
	{
		this.name = name;
		this.availableForAllAssemblies = true;
		
//		if(shouldBeMasterRecord)
//		{
//			this.assembly = new NsItemList<>();
//			this.assembly.addItem(new BOMAssembly(assembly, false, true));
//		}
	}
	
	@Getter @Setter String id;
	@Getter @Setter String name;
	@Getter @Setter boolean availableForAllAssemblies;
	@Getter @Setter NsItemList<NsID> bomRevision;
	@Getter @Setter NsItemList<NsID> restrictToAssemblies;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Getter @Setter LocalDateTime createdDate;
	// @Getter @Setter NsItemList<BOMAssembly> assembly; // Apparently netsuite doesn't like this because it's a sublist. Different option?
}
