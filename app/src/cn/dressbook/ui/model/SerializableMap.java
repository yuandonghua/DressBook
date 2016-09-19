package cn.dressbook.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializableMap implements Serializable {
	private HashMap<String, ArrayList<OrderForm>> map;

	public HashMap<String, ArrayList<OrderForm>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, ArrayList<OrderForm>> map) {
		this.map = map;
	}

}
