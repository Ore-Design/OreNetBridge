package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.api.orenetbridge.abstractions.ValueStorageRecord;
import design.ore.api.orenetbridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(value = "tableDisplay")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends ValueStorageRecord
{
	public Account(String id) { this.id = id; }
	
	@Getter @Setter String accountSearchDisplayName;
	@Getter @Setter String acctName;
	@Getter @Setter String acctNumber;
	@Getter @Setter NsID acctType;
	@Getter @Setter String description;
	@Getter @Setter String fullName;
	@Getter @Setter String refName;
	@Getter @Setter String id;
}
