package database;

import java.io.File;
import java.util.ArrayList;
import objects.Recipe;

public class Database {
	ArrayList<Recipe> recipes;

	public  boolean writeRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return false;
	}

	public  Recipe readRecipe(int id) {
		// TODO Auto-generated method stub
		return null;
	} 

	public Recipe deleteRecipe(String name){
		// TODO Auto-generated method stub
		return null;
	}

	public  ArrayList<File> lsReader() {
		File ls = new File("db/");
		ArrayList<File> list = new ArrayList<>();

		for (File file : ls.listFiles()) {
			if (file.isFile()) {
				list.add(file);
			} 
		}
		return list;
	}

}
