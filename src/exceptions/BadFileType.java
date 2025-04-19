package exceptions;

public class BadFileType extends Exception {

	public BadFileType() {
		super("Filename must end with .dat");
    }
	
}

