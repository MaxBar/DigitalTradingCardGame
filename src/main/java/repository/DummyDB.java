package repository;

import card.BasicCard;
import card.BasicCreatureCard;;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class  DummyDB {
   public List<BasicCard> database = new ArrayList<>(Arrays.asList(
        new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
        new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
        new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
        new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1),
        new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 2, 2, 2),
        new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 1, 3, 3),
        new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 2, 3, 5),
        new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 3, 2, 1),
        new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 1, 3, 3),
        new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet",1, 1, 1))
    );
}


