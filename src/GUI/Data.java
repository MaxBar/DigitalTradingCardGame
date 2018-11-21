package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;

public class Data {
    public int id;
    public String name;
    public int manaCost;
    public String img;
    public String flavourText;
    public String specialAbility;
    public int attackPower;
    public int defencePower;
    public int health;

    public Data(int id, String name, int manaCost, String img, String flavourText, String specialAbility, int attackPower, int defencePower, int health) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.img = img;
        this.flavourText = flavourText;
        this.specialAbility = specialAbility;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.health = health;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFlavourText() {
        return flavourText;
    }

    public void setFlavourText(String flavourText) {
        this.flavourText = flavourText;
    }

    public String getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(String specialAbility) {
        this.specialAbility = specialAbility;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefencePower() {
        return defencePower;
    }

    public void setDefencePower(int defencePower) {
        this.defencePower = defencePower;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
