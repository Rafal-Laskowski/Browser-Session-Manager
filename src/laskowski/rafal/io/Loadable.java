package laskowski.rafal.io;

import java.io.Serializable;

public interface Loadable<T> extends Serializable {
	public T load(String fileName);
}
