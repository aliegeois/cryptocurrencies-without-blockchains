package main;

/**
 * To be rewritten: the array not even dynamic at all!
 * @param <T> Type of the objects stored in the array
 */
public class DynamicArray<T> {
	
	private Object values[];
	
	public DynamicArray(){
		values = new Object[100];
	}
	
	@SuppressWarnings("unchecked")
	public T getOrCreate(int i, T val){
		if(values[i] == null){
			values[i] = val;
		}
		return (T) values[i];
	}
	
	public void set(int i, T val){
		values[i] = val;
	}

	public void freeUnder(int min) {
		for(int i =0; i < min; i++){
			values[i] = null;
		}
	}
}
