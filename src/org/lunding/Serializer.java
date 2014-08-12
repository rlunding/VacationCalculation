package org.lunding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

	
	public static void serializeEvent(Event event){
		try {
			FileOutputStream fileout = new FileOutputStream("oringen.event");
			ObjectOutputStream oos = new ObjectOutputStream(fileout);
			oos.writeObject(event);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Event deserialzeEvent(){
		try {
			FileInputStream filein = new FileInputStream("oringen.event");
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
