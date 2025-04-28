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

/**
 * Instancia una clase que permite el acceso a la base de datos de recetas e ingredientes
 * 
 * @author Grupo C [Backend]
 */
public final class Database {
    private static ObjectInputStream oif;
    private static ObjectOutputStream oof;
    private static FileOutputStream fos;
    private static RandomAccessFile raf;

    private ArrayList<Recipe> recipes;
    private ArrayList<File> files;
    private ArrayList<String> ingredients;
    private DataMap dataMap;

	/**
	 * Constructor de la clase Database
	 */
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

	/**
	 * Método que se encarga de popular la lista de recetas y la lista de archivos.
	 * 
	 * <p> Este método se encarga de popular la lista de recetas "recipes" y la lista de archivos "files" a partir de los archivos en el directorio "db/".
	 * Utiliza el método "lsReader()" para obtener la lista de archivos y luego intenta leer cada archivo como una receta.
	 * Si se encuentra un archivo que no es del tipo correcto o está dañado, se lanza una excepción correspondiente.</p>
	 */
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

	/**
	 * Método que se encarga de escribir una clase tipo "Recipe" en un archivo .dat
	 * @param recipe
	 * @return true si se escribio correctamente, false si no
	 * @throws NameAlreadyInUse Lanzado si el nombre de la receta ya existe en un archivo
	 * @throws EmptyObject Lanzado si el objeto receta es nulo
	 * 
	 * <p> Este método se encarga de escribir una clase tipo "Recipe" en un archivo .dat
	 * Primero verifica si el objeto "recipe" es nulo y lanza una excepción si lo es.
	 * Luego verifica si ya existe un archivo con el mismo nombre y lanza una excepción si es así.
	 * Si no hay problemas, crea un nuevo archivo .dat y escribe el objeto "recipe" en él.
	 * Finalmente, cierra los flujos de salida.
	 * Este método selecciona el nombre del archivo utilizando el nombre de la receta y se le agrega la extensión .dat</p>
	 */
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

	/**
	 * Método que se encarga de leer un archivo .dat y convertirlo en un objeto de tipo "Recipe".
	 * @param file Clase File que contiene datos de la ubicacion del archivo que guardara la receta
	 * @return Objeto de tipo Recipe
	 * @throws BadFileType Lanzado si el archivo no es .dat
	 * @throws CorruptedFile Lanzado si el archivo esta dañado
	 * <p> Este método se encarga de leer un archivo .dat y convertirlo en un objeto de tipo "Recipe"
	 * Primero verifica si el nombre del archivo termina con .dat y lanza una excepción si no es así.
	 * Luego intenta leer el archivo utilizando un flujo de entrada de objetos y lo convierte en un objeto "Recipe".
	 * Si hay un problema al leer el archivo, lanza una excepción de archivo dañado.
	 * Finalmente, cierra el flujo de entrada.</p>
	 */
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

	/**
	 * Método que se encarga de buscar un archivo .dat correspondiente a una receta.
	 * @param name Nombre de la receta
	 * @return Archivo de tipo File que contiene la receta
	 * <p> Este método se encarga de buscar un archivo .dat correspondiente a una receta.
	 * Itera a través de la lista de archivos "files" y si encuentra un archivo con el mismo nombre que la receta, lo devuelve.
	 * Si no encuentra el archivo, devuelve null.</p>
	 */
    public File searchRecipe(String name) {
        for (File i : files) {
            if (i.getName().equals(name + ".dat")) {
                return i;
            }
        }
        return null;
    }

	/**
	 * Método que se encarga de eliminar un archivo .dat correspondiente a una receta.
	 * @param name Nombre de la receta
	 * @return true si se elimino correctamente, false si no se encontro el archivo
	 * <p> Este método se encarga de eliminar un archivo .dat correspondiente a una receta.
	 * Busca en la lista de archivos "files" y si encuentra un archivo con el mismo nombre que la receta, lo elimina.
	 * Si se elimina un archivo, se repopula la lista recipes y files.
	 * Devuelve true si se eliminó un archivo y false si no se encontró el archivo.</p>
	 */
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

	/**
	 * Método que se encarga de leer el directorio "db/" y devolver una lista de archivos que contiene.
	 * @return Lista de archivos que contiene el directorio "db/"
	 * <p> Crea un objeto File que representa el directorio "db/" y luego itera a través de los archivos en ese directorio.
	 * Si un archivo es un archivo regular (no un directorio), lo agrega a la lista "list".
	 * Finalmente, devuelve la lista de archivos.
	 * El nombre del método viene de "ls" que es un comando de GNU Coreutils que lista los archivos en un directorio.</p>
	 */
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

	/**
	 * Método que se encarga de guardar el mapa de datos en un archivo.
	 * @return true si se guardo correctamente, false si no
	 * <p> Este método se encarga de guardar el mapa de datos en un archivo.
	 * Crea un flujo de salida de archivo y un flujo de salida de objetos para escribir el objeto "dataMap" en el archivo "db/datamap.db".
	 * Finalmente, cierra los flujos de salida.</p>
	 */
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

	/**
	 * Método que se encarga de cargar el mapa de datos desde un archivo.
	 * @return Objeto de tipo DataMap que contiene los datos
	 * <p> Este método se encarga de cargar el mapa de datos desde un archivo.
	 * Crea un objeto File que representa el archivo "db/datamap.db" y verifica si existe.
	 * Si el archivo existe, crea un flujo de entrada de objetos y lee el objeto "dataMap" desde el archivo.
	 * Finalmente, cierra el flujo de entrada y devuelve el objeto "dataMap".
	 * Si el archivo no existe, crea un nuevo objeto "dataMap" y lo devuelve.
	 * Si hay un problema al leer el archivo, devuelve null.</p>
	 */
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

	/**
	 * Método que se encarga de construir una cadena de texto a partir de los datos de un ingrediente.
	 * @param name Nombre del ingrediente
	 * @param type Tipo del ingrediente
	 * @param unit Unidad del ingrediente
	 * @return Cadena de texto que contiene los datos del ingrediente
	 * <p> Este método se encarga de construir una cadena de texto a partir de los datos de un ingrediente.
	 * Construye la cadena de texto utilizando el nombre, tipo y unidad del ingrediente, separados por el carácter "ƒ".
	 * Finalmente, agrega un salto de línea al final de la cadena.
	 * Devuelve la cadena de texto construida.</p>
	 */
    public String buildString(String name, String type, String unit) {
        String str;
        str = name + "ƒ" + type + "ƒ" + unit + "\n";
        return str;
    }

	/**
	 * Método que se encarga de analizar una cadena de texto y devolver un arreglo de cadenas.
	 * @param str Cadena de texto que contiene los datos del ingrediente
	 * @return Arreglo de cadenas que contiene los datos del ingrediente
	 * <p> Este método se encarga de analizar una cadena de texto y devolver un arreglo de cadenas.
	 * Divide la cadena utilizando el carácter "ƒ" como separador y verifica que la longitud del arreglo sea 3.
	 * Si la longitud no es 3, lanza una excepción de formato de cadena inválido.
	 * Si alguno de los campos está vacío, lanza una excepción de campo vacío.
	 * Si el último campo termina con un salto de línea, lo elimina.
	 * Finalmente, devuelve el arreglo de cadenas.</p>
	 * @throws IllegalArgumentException Lanzado si la cadena no tiene el formato correcto
	 * @throws IllegalArgumentException Lanzado si alguno de los campos está vacío
	 */
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

	/**
	 * Método que se encarga de agregar un ingrediente a la base de datos.
	 * @param name Nombre del ingrediente
	 * @param type Tipo del ingrediente
	 * @param unit Unidad del ingrediente
	 * @return true si se agrego correctamente, false si no
	 * <p> Este método se encarga de agregar un ingrediente a la base de datos.
	 * Construye una cadena de texto utilizando el nombre, tipo y unidad del ingrediente.
	 * Luego intenta abrir el archivo "db/ingredients.db" en modo de lectura y escritura.
	 * Si el archivo no existe, devuelve false.
	 * Escribe la cadena en el archivo y agrega el nombre del ingrediente al mapa de datos.
	 * Finalmente, guarda el mapa de datos y devuelve true si se agregó correctamente.
	 * Si hay un problema al abrir o escribir en el archivo, devuelve false.</p>
	 */
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

	/**
	 * Método que se encarga de agregar un ingrediente a la base de datos.
	 * @param parsedString Cadena de texto que contiene los datos del ingrediente
	 * @return true si se agrego correctamente, false si no
	 * <p> Este método se encarga de agregar un ingrediente a la base de datos.
	 * Intenta abrir el archivo "db/ingredients.db" en modo de lectura y escritura.
	 * Si el archivo no existe, devuelve false.
	 * Escribe la cadena en el archivo y agrega el nombre del ingrediente al mapa de datos.
	 * Finalmente, guarda el mapa de datos y devuelve true si se agregó correctamente.
	 * Si hay un problema al abrir o escribir en el archivo, devuelve false.</p>
	 */
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

	/**
	 * Método que se encarga de leer un ingrediente de la base de datos.
	 * @param name Nombre del ingrediente
	 * @return Arreglo de cadenas que contiene los datos del ingrediente
	 * <p> Este método se encarga de leer un ingrediente de la base de datos.
	 * Intenta abrir el archivo "db/ingredients.db" en modo de lectura.
	 * Busca el nombre del ingrediente en el mapa de datos y si lo encuentra, lee la cadena correspondiente.
	 * Luego analiza la cadena y la agrega a la lista de ingredientes.
	 * Finalmente, devuelve el arreglo de cadenas que contiene los datos del ingrediente.
	 * Si no encuentra el ingrediente, devuelve null.</p>
	 */
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

	/**
	 * Método que se encarga de eliminar un ingrediente de la base de datos.
	 * @param name Nombre del ingrediente
	 * @return true si se elimino correctamente, false si no se encontro el ingrediente
	 * <p> Este método se encarga de eliminar un ingrediente de la base de datos.
	 * Invoca al método "removeData" del objeto "dataMap" para eliminar el ingrediente. </p>
	 * <p> Si el ingrediente se elimina correctamente, devuelve true. </p>
	 * @see objects.DataMap#removeData(String)
	 */
	public boolean deleteIngredient(String name) {
		return dataMap.removeData(name);
	}

	/**
	 * Método que se encarga de obtener la lista de ingredientes.
	 * @return Lista de ingredientes
	 */
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

	/**
	 * Método que se encarga de obtener la lista de recetas.
	 * @return Lista de recetas
	 * <p> Este método se encarga de obtener la lista de recetas.
	 * Este método debe de ser llamado despues de cualquier operacion de escritura o borrado de archivos.
	 * Esto es para que la lista de archivos se mantenga actualizada.</p>
	 * @see #populateArrayLists()
	 */
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

	/**
	 * Método que se encarga de obtener la lista de archivos.
	 * @return Lista de archivos
	 * <p> Este método se encarga de obtener la lista de archivos.
	 * Este método debe de ser llamado despues de cualquier operacion de escritura o borrado de archivos.
	 * Esto es para que la lista de archivos se mantenga actualizada.</p>
	 * @see #populateArrayLists()
	 */
    public ArrayList<File> getFiles() {
        return files;
    }
}
