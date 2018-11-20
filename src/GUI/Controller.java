/*
package GUI;
import Assets.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.input.*;


import javafx.scene.layout.Pane;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private List<BasicCard> playerAHand;
    private List<BasicCard> tableCards;
    @FXML ArrayList<Data> data = new ArrayList<Data>();

    BasicCreatureCard card = null;

    //HAND CARD 1
    @FXML private Pane handPane1;
    @FXML private Label handCardNameLabel1;
    @FXML private Label handCardMcLabel1;
    @FXML private ImageView handimg1;
    @FXML private Label handCardFlavourTextLabel1;
    @FXML private Label handCardADLabel1;
    @FXML private Label handCardDPLabel1;
    @FXML private Label handCardHPLabel1;

    //HAND CARD 2
    @FXML private Pane handPane2;
    @FXML private Label handCardNameLabel2;
    @FXML private Label handCardMcLabel2;
    @FXML private ImageView handimg2;
    @FXML private Label handCardFlavourTextLabel2;
    @FXML private Label handCardADLabel2;
    @FXML private Label handCardDPLabel2;
    @FXML private Label handCardHPLabel2;

    //HAND CARD 3
    @FXML private Pane handPane3;
    @FXML private Label handCardNameLabel3;
    @FXML private Label handCardMcLabel3;
    @FXML private ImageView handimg3;
    @FXML private Label handCardFlavourTextLabel3;
    @FXML private Label handCardADLabel3;
    @FXML private Label handCardDPLabel3;
    @FXML private Label handCardHPLabel3;

    //HAND CARD 4
    @FXML private Pane handPane4;
    @FXML private Label handCardNameLabel4;
    @FXML private Label handCardMcLabel4;
    @FXML private ImageView handimg4;
    @FXML private Label handCardFlavourTextLabel4;
    @FXML private Label handCardADLabel4;
    @FXML private Label handCardDPLabel4;
    @FXML private Label handCardHPLabel4;

    //HAND CARD 5
    @FXML private Pane handPane5;
    @FXML private Label handCardNameLabel5;
    @FXML private Label handCardMcLabel5;
    @FXML private ImageView handimg5;
    @FXML private Label handCardFlavourTextLabel5;
    @FXML private Label handCardADLabel5;
    @FXML private Label handCardDPLabel5;
    @FXML private Label handCardHPLabel5;

    //HAND CARD 6
    @FXML private Pane handPane6;
    @FXML private Label handCardNameLabel6;
    @FXML private Label handCardMcLabel6;
    @FXML private ImageView handimg6;
    @FXML private Label handCardFlavourTextLabel6;
    @FXML private Label handCardADLabel6;
    @FXML private Label handCardDPLabel6;
    @FXML private Label handCardHPLabel6;

    //HAND CARD 7
    @FXML private Pane handPane7;
    @FXML private Label handCardNameLabel7;
    @FXML private Label handCardMcLabel7;
    @FXML private ImageView handimg7;
    @FXML private Label handCardFlavourTextLabel7;
    @FXML private Label handCardADLabel7;
    @FXML private Label handCardDPLabel7;
    @FXML private Label handCardHPLabel7;

    //HAND CARD 8
    @FXML private Pane handPane8;
    @FXML private Label handCardNameLabel8;
    @FXML private Label handCardMcLabel8;
    @FXML private ImageView handimg8;
    @FXML private Label handCardFlavourTextLabel8;
    @FXML private Label handCardADLabel8;
    @FXML private Label handCardDPLabel8;
    @FXML private Label handCardHPLabel8;

    //HAND CARD 9
    @FXML private Pane handPane9;
    @FXML private Label handCardNameLabel9;
    @FXML private Label handCardMcLabel9;
    @FXML private ImageView handimg9;
    @FXML private Label handCardFlavourTextLabel9;
    @FXML private Label handCardADLabel9;
    @FXML private Label handCardDPLabel9;
    @FXML private Label handCardHPLabel9;

    //HAND CARD 10
    @FXML private Pane handPane10;
    @FXML private Label handCardNameLabel10;
    @FXML private Label handCardMcLabel10;
    @FXML private ImageView handimg10;
    @FXML private Label handCardFlavourTextLabel10;
    @FXML private Label handCardADLabel10;
    @FXML private Label handCardDPLabel10;
    @FXML private Label handCardHPLabel10;

    //TABLE CARD 1
    @FXML private Pane tablePane1;
    @FXML private Label tableCardNameLabel1;
    @FXML private Label tableCardMcLabel1;
    @FXML private ImageView tableimg1;
    @FXML private Label tableCardFlavourTextLabel1;
    @FXML private Label tableCardADLabel1;
    @FXML private Label tableCardDPLabel1;
    @FXML private Label tableCardHPLabel1;

    //TABLE CARD 2
    @FXML private Pane tablePane2;
    @FXML private Label tableCardNameLabel2;
    @FXML private Label tableCardMcLabel2;
    @FXML private ImageView tableimg2;
    @FXML private Label tableCardFlavourTextLabel2;
    @FXML private Label tableCardADLabel2;
    @FXML private Label tableCardDPLabel2;
    @FXML private Label tableCardHPLabel2;

    //TABLE CARD 3
    @FXML private Pane tablePane3;
    @FXML private Label tableCardNameLabel3;
    @FXML private Label tableCardMcLabel3;
    @FXML private ImageView tableimg3;
    @FXML private Label tableCardFlavourTextLabel3;
    @FXML private Label tableCardADLabel3;
    @FXML private Label tableCardDPLabel3;
    @FXML private Label tableCardHPLabel3;

    //TABLE CARD 4
    @FXML private Pane tablePane4;
    @FXML private Label tableCardNameLabel4;
    @FXML private Label tableCardMcLabel4;
    @FXML private ImageView tableimg4;
    @FXML private Label tableCardFlavourTextLabel4;
    @FXML private Label tableCardADLabel4;
    @FXML private Label tableCardDPLabel4;
    @FXML private Label tableCardHPLabel4;

    //TABLE CARD 5
    @FXML private Pane tablePane5;
    @FXML private Label tableCardNameLabel5;
    @FXML private Label tableCardMcLabel5;
    @FXML private ImageView tableimg5;
    @FXML private Label tableCardFlavourTextLabel5;
    @FXML private Label tableCardADLabel5;
    @FXML private Label tableCardDPLabel5;
    @FXML private Label tableCardHPLabel5;

    //ENEMY TABLE CARD 1
    @FXML private Pane enemyTablePane1;
    @FXML private Label enemyTableCardNameLabel1;
    @FXML private Label enemyTableCardMcLabel1;
    @FXML private ImageView enemyImg1;
    @FXML private Label enemyTableCardFlavourTextLabel1;
    @FXML private Label enemyTableCardADLabel1;
    @FXML private Label enemyTableCardDPLabel1;
    @FXML private Label enemyTableCardHPLabel1;

    //ENEMY TABLE CARD 2
    @FXML private Pane enemyTablePane2;
    @FXML private Label enemyTableCardNameLabel2;
    @FXML private Label enemyTableCardMcLabel2;
    @FXML private ImageView enemyImg2;
    @FXML private Label enemyTableCardFlavourTextLabel2;
    @FXML private Label enemyTableCardADLabel2;
    @FXML private Label enemyTableCardDPLabel2;
    @FXML private Label enemyTableCardHPLabel2;

    //ENEMY TABLE CARD 3
    @FXML private Pane enemyTablePane3;
    @FXML private Label enemyTableCardNameLabel3;
    @FXML private Label enemyTableCardMcLabel3;
    @FXML private ImageView enemyImg3;
    @FXML private Label enemyTableCardFlavourTextLabel3;
    @FXML private Label enemyTableCardADLabel3;
    @FXML private Label enemyTableCardDPLabel3;
    @FXML private Label enemyTableCardHPLabel3;

    //ENEMY TABLE CARD 4
    @FXML private Pane enemyTablePane4;
    @FXML private Label enemyTableCardNameLabel4;
    @FXML private Label enemyTableCardMcLabel4;
    @FXML private ImageView enemyImg4;
    @FXML private Label enemyTableCardFlavourTextLabel4;
    @FXML private Label enemyTableCardADLabel4;
    @FXML private Label enemyTableCardDPLabel4;
    @FXML private Label enemyTableCardHPLabel4;

    //ENEMY TABLE CARD 5
    @FXML private Pane enemyTablePane5;
    @FXML private Label enemyTableCardNameLabel5;
    @FXML private Label enemyTableCardMcLabel5;
    @FXML private ImageView enemyImg5;
    @FXML private Label enemyTableCardFlavourTextLabel5;
    @FXML private Label enemyTableCardADLabel5;
    @FXML private Label enemyTableCardDPLabel5;
    @FXML private Label enemyTableCardHPLabel5;

    //ENEMY HAND
    @FXML private Pane enemyHandPane1;
    @FXML private Pane enemyHandPane2;
    @FXML private Pane enemyHandPane3;
    @FXML private Pane enemyHandPane4;
    @FXML private Pane enemyHandPane5;
    @FXML private Pane enemyHandPane6;
    @FXML private Pane enemyHandPane7;
    @FXML private Pane enemyHandPane8;
    @FXML private Pane enemyHandPane9;
    @FXML private Pane enemyHandPane10;

    // PLAYER
    @FXML private Label player;
    @FXML private Label playerHP;
    @FXML private Label playerMana;

    // ENEMY
    @FXML private Label enemy;
    @FXML private Label enemyHP;
    @FXML private Label enemyMana;

    //PLAYER GRAVEYARD
    @FXML private Label playerGraveyard;

    //ENEMY GRAVEYARD
    @FXML private Label enemyGraveyard;

    //PLAYER DECK
    @FXML private Label playerDeck;

    //ENEMYDECK
    @FXML private Label enemyDeck;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableCards = new ArrayList<>();
        playerAHand = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2,1,1),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not exist", 0, 2, 1,1,1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1,1,1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1,1,1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2,1,1),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3,1,1),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5,1,1),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1,1,1),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 31,1,1),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet",0, 1, 1,1,1))
        );
        //Player Hand
         //   handCardName1.setText(playerAHand.get(0).getName());
            handCardMc1.setText(Integer.toString(playerAHand.get(0).getManaCost()));
            handCardFlavourText1.setText((playerAHand.get(0).getFlavourText()));
            //tableCardSpecialAbilityLabel1.setText((playerADeck.get(0).getSp()));
            handCardADDP1.setText(Integer.toString(((BasicCreatureCard) playerAHand.get(0)).getAttack()));
            handCardHP1.setText(Integer.toString(((BasicCreatureCard) playerAHand.get(0)).getHealth()));




    }
    public void mouseClick() {
        pane1.setOnMouseClicked((MouseEvent event) -> {
            int index = tableCards.size();
//            Dragboard db = pane1.startDragAndDrop(TransferMode.MOVE);
            card = (BasicCreatureCard)playerAHand.get(0);
            tableCards.add(card);
            playerAHand.remove(0);
            updateCards();

            event.consume();
        });
    }
    public void updateCards(){
        if(1 <= playerAHand.size()) {
            handCardName1.setText(playerAHand.get(0).getName());
            handCardMc1.setText(Integer.toString(playerAHand.get(0).getManaCost()));
            handCardFlavourText1.setText((playerAHand.get(0).getFlavourText()));
            handCardADDP1.setText(Integer.toString(((BasicCreatureCard) playerAHand.get(0)).getAttack()));
            handCardHP1.setText(Integer.toString(((BasicCreatureCard) playerAHand.get(0)).getHealth()));
        }
        if(1 <= tableCards.size()) {
            tableCardNameLabel1.setText(tableCards.get(0).getName());
            tableCardMcLabel1.setText(Integer.toString(tableCards.get(0).getManaCost()));
            tableCardFlavourTextLabel1.setText((tableCards.get(0).getFlavourText()));
            tableCardADDPLabel1.setText(Integer.toString(((BasicCreatureCard)tableCards.get(0)).getAttack()));
            tableCardHPLabel1.setText(Integer.toString(((BasicCreatureCard)tableCards.get(0)).getHealth()));
        }     if(2 <= tableCards.size()) {
            tableCardNameLabel2.setText(tableCards.get(1).getName());
            tableCardMcLabel2.setText(Integer.toString(tableCards.get(1).getManaCost()));
            tableCardFlavourTextLabel2.setText((tableCards.get(1).getFlavourText()));
            tableCardADDPLabel2.setText(Integer.toString(((BasicCreatureCard)tableCards.get(1)).getAttack()));
            tableCardHPLabel2.setText(Integer.toString(((BasicCreatureCard)tableCards.get(1)).getHealth()));
        }

        if(1 > playerAHand.size()) {
            handCardName1.setText("");
            handCardMc1.setText("");
            handCardFlavourText1.setText("");
            handCardADDP1.setText("");
            handCardHP1.setText("");
        }

    }

}
*/
