package io.github.danielthedev.passwords;

public final class Unsafe {
	
	@FunctionalInterface
	public interface Function<T, K> {

		K accept(T t) throws Exception;
		
	}

	@FunctionalInterface
	public interface Consumer<T> {

		void accept(T t) throws Exception;
		
	}
	
}


