package design.ore.OreNetBridge.Generic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
public class QueryResults<T>
{
	@Getter @Setter int count = 0;
	@Getter @Setter boolean hasMore = false;
	@Getter List<T> items = new ArrayList<>();
	
	public void setItems(List<T> items) { this.items = new ArrayList<>(); this.items.addAll(items); }

	public void merge(QueryResults<T> results)
	{
		items.addAll(results.getItems());
		count += results.getCount();
		hasMore = results.isHasMore();
	}
}
