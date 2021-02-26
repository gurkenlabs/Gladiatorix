package de.litigame;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.litigame.items.*;
import de.litigame.hotbar.*;
import de.litigame.entities.Player;

@XmlRootElement(name = "savegame") 
public class SaveGame {
	
	@XmlElement(name = "storage")
	private Item[] storage;
	
	@XmlElement(name = "money")
	private int money;
	
	@XmlElement(name = "level")
	private int level;
	
	@XmlElement(name = "xPos")
	private int xPos;
	
	@XmlElement(name="yPos")
	public int yPos;
	
	@XmlElement 
	
		
}
		
	

