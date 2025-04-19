package database;

import exceptions.BadFileType;
import exceptions.CorruptedFile;
import exceptions.EmptyObject;
import exceptions.NameAlreadyInUse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import objects.Recipe;

public class Database {
	private static ObjectInputStream oif;
    private static ObjectOutputStream oof;
	private static FileOutputStream fos;

	private ArrayList<Recipe> recipes;
	private ArrayList<File> files;
	
	public Database() {
		populateArrayLists();
	}

	// DOC: Este método se encarga de popular la lista de recetas "recipes" y la lista de archivos "files" a partir de los archivos en el directorio "db/".
	// DOC: Utiliza el método "lsReader()" para obtener la lista de archivos y luego intenta leer cada archivo como una receta.
	// DOC: Si se encuentra un archivo que no es del tipo correcto o está dañado, se lanza una excepción correspondiente.
	private void populateArrayLists(){
		files = lsReader();
		recipes = new ArrayList<>();
		for (File i : files) {
			try {
				recipes.add(readRecipe(i));
			} catch (BadFileType | CorruptedFile e) {
				e.printStackTrace();
			}
		}
	}

	// DOC: Este método se encarga de escribir una clase tipo "Recipe" en un archivo .dat
	// DOC: Primero verifica si el objeto "recipe" es nulo y lanza una excepción si lo es.
	// DOC: Luego verifica si ya existe un archivo con el mismo nombre y lanza una excepción si es así.
	// DOC: Si no hay problemas, crea un nuevo archivo .dat y escribe el objeto "recipe" en él.
	// DOC: Finalmente, cierra los flujos de salida.
	// DOC: Este método selecciona el nombre del archivo utilizando el nombre de la receta y se le agrega la extensión .dat
	public  boolean writeRecipe(Recipe recipe) throws NameAlreadyInUse, EmptyObject{
		if (recipe == null) {
			throw new EmptyObject();
		}
		for (File i : files) {
			if (i.getName().equals(recipe.getName()+".dat")) {
				throw new NameAlreadyInUse();
			}
			
		}
		try {
			fos = new FileOutputStream("db/" + recipe.getName() + ".dat");
			oof = new ObjectOutputStream(fos);
			oof.writeObject(recipe);
			oof.close();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// DOC: Este método se encarga de leer un archivo .dat y convertirlo en un objeto de tipo "Recipe".
	// DOC: Primero verifica si el nombre del archivo termina con .dat y lanza una excepción si no es así.
	// DOC: Luego intenta leer el archivo utilizando un flujo de entrada de objetos y lo convierte en un objeto "Recipe".
	// DOC: Si hay un problema al leer el archivo, lanza una excepción de archivo dañado.
	// DOC: Finalmente, cierra el flujo de entrada.
	public  Recipe readRecipe(File file) throws BadFileType, CorruptedFile{
		if (file.getName().endsWith(".dat")) {
			try {
				oif = new ObjectInputStream(new FileInputStream(file));
				return (Recipe) oif.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new CorruptedFile();
			} finally {
				try {
					oif.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new BadFileType();
		}
	} 

	// DOC: Este método se encarga de buscar un archivo .dat correspondiente a una receta.
	// DOC: Itera a través de la lista de archivos "files" y si encuentra un archivo con el mismo nombre que la receta, lo devuelve.
	// DOC: Si no encuentra el archivo, devuelve null.
	public File searchRecipe(String name) {
		for (File i : files) {
			if (i.getName().equals(name + ".dat")) {
				return i;
			}
		}
		return null;
	}

	// DOC: Este método se encarga de eliminar un archivo .dat correspondiente a una receta.
	// DOC: Busca en la lista de archivos "files" y si encuentra un archivo con el mismo nombre que la receta, lo elimina.
	// DOC: Si se elimina un archivo, se repopula la lista recipes y files.
	// DOC: Devuelve true si se eliminó un archivo y false si no se encontró el archivo.
	public boolean deleteRecipe(String name){
		boolean toRepopulate = false;
		for (File i : files) {
			if (i.getName().equals(name + ".dat")) {
				System.out.println("Deleting " + i.getName());
				i.delete();
				toRepopulate = true;
			}
		}
		if (toRepopulate) {
			populateArrayLists();
			return true;
		} else {
			return false;
		}
	}

	// DOC: Este método se encarga de leer el directorio "db/" y devolver una lista de archivos que contiene.
	// DOC: Crea un objeto File que representa el directorio "db/" y luego itera a través de los archivos en ese directorio.
	// DOC: Si un archivo es un archivo regular (no un directorio), lo agrega a la lista "list".
	// DOC: Finalmente, devuelve la lista de archivos.
	// DOC: El nombre del método viene de "ls" que es un comando de GNU Coreutils que lista los archivos en un directorio.
	public final ArrayList<File> lsReader() {
		File ls = new File("db/");
		ArrayList<File> list = new ArrayList<>();

		for (File file : ls.listFiles()) {
			if (file.isFile()) {
				list.add(file);
			} 
		}
		return list;
	}

	// DOC: Este método se encarga de obtener la lista de recetas.
	// DOC: Este método debe de ser llamado despues de cualquier operacion de escritura o borrado de archivos.
	// DOC: Esto es para que la lista de archivos se mantenga actualizada.
	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}

	// DOC: Este método se encarga de obtener la lista de archivos.
	// DOC: Este método debe de ser llamado despues de cualquier operacion de escritura o borrado de archivos.
	// DOC: Esto es para que la lista de archivos se mantenga actualizada.
	public ArrayList<File> getFiles() {
		return files;
	}
	
}
