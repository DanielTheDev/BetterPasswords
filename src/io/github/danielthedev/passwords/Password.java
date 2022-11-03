package io.github.danielthedev.passwords;

import java.util.function.Supplier;

public class Password {

	private final String hint;
	private final String password;
	
	private Password(String password, String hint) {
		this.hint = hint;
		this.password = password;
	}
	
	public static Password of(String password, String hint) {
		return new Password(password, hint);
	}

	public String getHint() {
		return hint;
	}

	public String getPassword() {
		return password;
	}
	
	public static class Future {
		
		private final Supplier<String> hintSupplier;
		private final Supplier<String> passwordSupplier;
		
		public Future(Supplier<String> passwordSupplier, Supplier<String> hintSupplier) {
			this.hintSupplier = hintSupplier;
			this.passwordSupplier = passwordSupplier;
		}
		
		public Password getPassword() {
			return new Password(passwordSupplier.get(), hintSupplier.get());
		}
	}
}
