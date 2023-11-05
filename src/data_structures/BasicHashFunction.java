package data_structures;

import interfaces.HashFunction;

public class BasicHashFunction implements HashFunction<Integer> {

	@Override
	public int hashCode(Integer key) {
		return key;
	}

}
