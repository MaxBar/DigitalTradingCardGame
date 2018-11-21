package Assets;

public class BasicCard {
    public int id;
    String name;
    String flavourText;
    String image;
    boolean isConsumed;
    int manaCost;

    public BasicCard(int id, String name, String flavourText, String image, int manaCost) {
        this.id = id;
        this.name = name;
        this.flavourText = flavourText;
        this.image = image;
        this.manaCost = manaCost;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlavourText() {
        return flavourText;
    }

    public String getImage() {
        return image;
    }

    public boolean getIsConsumed() { return isConsumed;   }

    public void setIsConsumed(boolean isConsumed) { this.isConsumed = isConsumed; }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
