package org.lunding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

	
	public static boolean serializeEvent(Event event, String filename){
		try {
			FileOutputStream fileout = new FileOutputStream(filename + ".event");
			ObjectOutputStream oos = new ObjectOutputStream(fileout);
			oos.writeObject(event);
			oos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Event deserialzeEvent(File file){
		try {
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(filein);
			Event event = (Event) ois.readObject();
			ois.close();
			return event;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
