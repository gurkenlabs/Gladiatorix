package de.litigame.weapons;

public class Weapon {

	public final WeaponAttributes attributes = new WeaponAttributes();
	public final WeaponType type;

	public Weapon(WeaponType type) {
		this.type = type;
	}
}
