package database;

import exceptions.BadFileType;
import exceptions.CorruptedFile;
import exceptions.EmptyObject;
import exceptions.NameAlreadyInUse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objects.DataMap;
import objects.Recipe;

public final class Database {

    private static ObjectInputStream oif;
    private static ObjectOutputStream oof;
    private static FileOutputStream fos;
    private static RandomAccessFile raf;

    private ArrayList<Recipe> recipes;
    private ArrayList<File> files;
    private ArrayList<String> ingredients;
    private DataMap dataMap;

    public Database() {
		dataMap = loadDataMap();
        populateArrayLists();
		if (dataMap == null) {
			dataMap = new DataMap("datamap");
		}
    }

    // DOC: Este método se encarga de popular la lista de recetas "recipes" y la lista de archivos "files" a partir de los archivos en el directorio "db/".
    // DOC: Utiliza el método "lsReader()" para obtener la lista de archivos y luego intenta leer cada archivo como una receta.
    // DOC: Si se encuentra un archivo que no es del tipo correcto o está dañado, se lanza una excepción correspondiente.
    private void populateArrayLists() {
        files = lsReader();
        recipes = new ArrayList<>();
		
        for (File i : files) {
            try {
                recipes.add(readRecipe(i));
            } catch (BadFileType | CorruptedFile e) {
                e.printStackTrace();
            }
        }

		if (ingredients == null) {
			ingredients = new ArrayList<>();
		}
		for (String i : dataMap.getData()) {
			ingredients.add(i);
		}
    }

    // DOC: Este método se encarga de escribir una clase tipo "Recipe" en un archivo .dat
    // DOC: Primero verifica si el objeto "recipe" es nulo y lanza una excepción si lo es.
    // DOC: Luego verifica si ya existe un archivo con el mismo nombre y lanza una excepción si es así.
    // DOC: Si no hay problemas, crea un nuevo archivo .dat y escribe el objeto "recipe" en él.
    // DOC: Finalmente, cierra los flujos de salida.
    // DOC: Este método selecciona el nombre del archivo utilizando el nombre de la receta y se le agrega la extensión .dat
    public boolean writeRecipe(Recipe recipe) throws NameAlreadyInUse, EmptyObject {
        if (recipe == null) {
            throw new EmptyObject();
        }
        for (File i : files) {
            if (i.getName().equals(recipe.getName() + ".dat")) {
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
    public Recipe readRecipe(File file) throws BadFileType, CorruptedFile {
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
    public boolean deleteRecipe(String name) {
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
            if (file.isFile() && file.getName().endsWith(".dat")) {
                list.add(file);
            }
        }
        return list;
    }

    public boolean saveDataMap() {
        try {
            fos = new FileOutputStream("db/datamap.db");
            oof = new ObjectOutputStream(fos);
            oof.writeObject(dataMap);
            oof.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

	public DataMap loadDataMap() {
		try {
			File file = new File("db/datamap.db");
			if (file.exists()) {
				oif = new ObjectInputStream(new FileInputStream(file));
				dataMap = (DataMap) oif.readObject();
				oif.close();
				return dataMap;
			} else {
				dataMap = new DataMap("datamap");
				return dataMap;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

    public String buildString(String name, String type, String unit) {
        String str;
        str = name + "ƒ" + type + "ƒ" + unit + "\n";
        return str;
    }

    public String[] parseString(String str) {
        String[] parts = str.split("ƒ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid string format");
        }
        if (parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) {
            throw new IllegalArgumentException("Empty fields in string");
        }
        if (parts[2].endsWith("\n")) {
            parts[2] = parts[2].substring(0, parts[2].length() - 1);
        }
        return parts;
    }

    public boolean addIngredient(String name, String type, String unit) {
        String parsedString = buildString(name, type, unit);

        try {
            raf = new RandomAccessFile("db/ingredients.db", "rw");
            raf.seek(raf.length());
            raf.writeUTF(parsedString);
            dataMap.addData(name, parsedString.getBytes().length);
			ingredients.add(parseString(parsedString)[0]);
			saveDataMap();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addIngredient(String parsedString) {
        if (parsedString == null || parsedString.isEmpty()) {
            throw new IllegalArgumentException("Parsed string cannot be null or empty");
        }
        if (!parsedString.endsWith("\n")) {
            parsedString += "\n";
        }
        try {
            raf = new RandomAccessFile("db/ingredients.db", "rw");
            raf.seek(raf.length());
            raf.writeUTF(parsedString);
            dataMap.addData(parseString(parsedString)[0], parsedString.getBytes().length);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

	public String[] readIngredient(String name) {
		try {
			raf = new RandomAccessFile("db/ingredients.db", "r");
			
			String[] data = dataMap.searchData(name);

			if (data != null) {
				raf.skipBytes((int) (raf.getFilePointer() - Long.parseLong(data[1])));
				String[] str = parseString(raf.readUTF());
				ingredients.add(str[0]);
				return str;
			} else {
				return null;
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteIngredient(String name) {
		return dataMap.removeData(name);
	}

    public ArrayList<String> getIngredients() {
        return ingredients;
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
