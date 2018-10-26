package card;

public class BasicCard {
    int id;
    String name;
    String flavourText;
    String image;

    public BasicCard(int id, String name, String flavourText, String image) {
        this.id = id;
        this.name = name;
        this.flavourText = flavourText;
        this.image = image;
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
}
