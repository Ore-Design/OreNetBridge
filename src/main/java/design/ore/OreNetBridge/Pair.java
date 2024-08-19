package design.ore.OreNetBridge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<T, V>
{
	T key;
	V value;
}
