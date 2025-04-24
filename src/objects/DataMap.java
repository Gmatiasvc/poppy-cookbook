package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class DataMap implements Serializable {

    private String id;
    private ArrayList<String> data;
    private ArrayList<Integer> loc;
    private ArrayList<Integer> size;
	private int byteTotalSize;

    public DataMap(String id) {
        this.id = id;
        data = new ArrayList<>();
        loc = new ArrayList<>();
        size = new ArrayList<>();
		byteTotalSize = 0;
    }

	public void addData(String data, int size) {
		this.data.add(data);
		byteTotalSize += size;
		this.loc.add(byteTotalSize);
		this.size.add(size);
	}

	public boolean removeData(String name) {
		int index = data.indexOf(name);
		if (index != -1) {
			data.remove(index);
			loc.remove(index);
			size.remove(index);
			return true;
		}
		return false;
	}

	public String[] searchData(String name) {
		int index = data.indexOf(name);
		if (index != -1) {
			String[] result = new String[3];
			result[0] = data.get(index);
			result[1] = String.valueOf(loc.get(index));
			result[2] = String.valueOf(size.get(index));
			return result;
		}
		return null;
	}

	public ArrayList<String> getData() {
		return data;
	}

    public String getId() {
        return id;
    }

}
