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

	ArrayList<Recipe> recipes;
	ArrayList<File> files;
	
	public Database() {
		files = lsReader();
	}
        @SuppressWarnings("UseSpecificCatch")
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

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

	public boolean  deleteRecipe(File file){
		return true;
	}

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

	public static void main(String[] args) {
	Database db = new Database();
		for (File i : db.lsReader()) {
			System.out.println(i.getName());
		}
		Recipe r = new Recipe("test2", "test", new ArrayList<>(), new ArrayList<>(), 15, 20);
		try {
			db.writeRecipe(r);
		} catch (NameAlreadyInUse e) {
			e.printStackTrace();
		} catch (EmptyObject e) {
			e.printStackTrace();
		}
		
		
		
		try {
			System.out.println(db.readRecipe(new File("db/test2.dat")).toString());
		} catch (BadFileType e) {
			e.printStackTrace();
		} catch (CorruptedFile e) {
			e.printStackTrace();
		}		
	}
}
