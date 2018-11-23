package main;

import card.EKeyword;

public class Card {

    String name;
    int manaCost;
    String image;
    String ability;
    String special;
   // EKeyword keyword;
    String flavourText;
    int health;
    int attack;
    int defense;


    public Card(String name, int manaCost, String image, String ability, String special, String flavourText, int health, int attack, int defense) {
        this.name = name;
        this.manaCost = manaCost;
        this.image = image;
        this.ability = ability;
        this.special = special;
        this.flavourText = flavourText;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public String getSpecial() {
        return special;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getImage() {
        return image;
    }

    public String getAbility() {
        return ability;
    }

    public String getFlavourText() {
        return flavourText;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}
