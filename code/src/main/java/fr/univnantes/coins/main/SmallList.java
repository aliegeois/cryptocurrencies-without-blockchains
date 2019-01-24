package fr.univnantes.coins.main;

/**
 * Should be rewritten: the array is actually not dynamic at all!
 * @param <T> Type of the objects stored in the array
 */
public class SmallList<T> {
	private Object values[] = new Object[100];
	
	@SuppressWarnings("unchecked")
	public T agetOrCreate(int i, T val) {
		if(values[i] == null) {
			values[i] = val;
		}

		return (T)values[i];
	}
	
	public void aset(int i, T val) {
		values[i] = val;
	}

	public void freeUnder(int min) {
		for(int i = 0; i < min; i++) {
			values[i] = null;
		}
	}
}
