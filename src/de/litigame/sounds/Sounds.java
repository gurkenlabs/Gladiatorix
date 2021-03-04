package de.litigame.sounds;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

public class Sounds {
	
static Sound sword;
static Sound step;
static Sound hit;
static Sound gulp;
static Sound grunt;
static Sound equipSword;
static Sound equipArmor;

public static Sound getSword() {
	return sword;
}

public static Sound getStep() {
	return step;
}

public static Sound getHit() {
	return hit;
}

public static Sound getGulp() {
	return gulp;
}

public static Sound getGrunt() {
	return grunt;
}

public static Sound getEquipSword() {
	return equipSword;
}

public static Sound getEquipArmor() {
	return equipArmor;
}


public static void init() throws FileNotFoundException, UnsupportedAudioFileException, IOException{
	try {
		sword = new Sound(new BufferedInputStream(new FileInputStream("sounds/sword.wav")), "sword");
		step = new Sound(new BufferedInputStream(new FileInputStream("sounds/step.wav")), "step");
		hit = new Sound(new BufferedInputStream(new FileInputStream("sounds/hit.wav")), "hit");
		gulp = new Sound(new BufferedInputStream(new FileInputStream("sounds/gulp.wav")), "gulp");
		grunt = new Sound(new BufferedInputStream(new FileInputStream("sounds/grunt.wav")), "grunt");
		equipSword = new Sound(new BufferedInputStream(new FileInputStream("sounds/equip_sword.wav")), "equipSword");
		equipArmor = new Sound(new BufferedInputStream(new FileInputStream("sounds/equip_armor.wav")), "equipArmor");
	}
	catch(UnsupportedAudioFileException | IOException e) {
		e.printStackTrace();
	}
	
	Resources.sounds().add("sword", sword);
	Resources.sounds().add("step", step);
	Resources.sounds().add("hit", hit);
	Resources.sounds().add("gulp", gulp);
	Resources.sounds().add("grunt", grunt);
	Resources.sounds().add("equipSword", equipSword);
	Resources.sounds().add("equipArmor", equipArmor);
}
	
	

	
	

}
