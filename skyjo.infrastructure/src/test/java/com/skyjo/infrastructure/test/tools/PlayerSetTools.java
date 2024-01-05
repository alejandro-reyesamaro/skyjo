package com.skyjo.infrastructure.test.tools;

import java.util.ArrayList;
import java.util.List;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardColumn;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.TableCard;

public class PlayerSetTools {

    public static PlayerSet createPlayerSet(int index, int nbHiddenCards) { 
        return PlayerSetTools.createWithHiddenCards(index, true, nbHiddenCards);
    }

    public static PlayerSet createWithHiddenCards(int index, boolean isPlaying, int hiddenCardsCount) {
        List<CardColumn> columnList = new ArrayList<CardColumn>();
        for(int col = 0; col < 4; col++){
            List<TableCard> cards = new ArrayList<TableCard>();
            for (int row = 0; row < 3; row++){
                boolean isShown = hiddenCardsCount-- <= 0;
                Card card = new Card((col + 1) * (row + 1), isShown);
                TableCard tCard = new TableCard(row, card);
                cards.add(tCard);
            }
            CardColumn column = new CardColumn(col, cards);
            columnList.add(column);
        }
        Player player = new Player();
        player.setName(String.format("Player_%d",index));
        player.setServiceAddress(String.format("http://localhost/%d",index));
        PlayerSet ps = new PlayerSet(columnList, player);
        ps.setPlaying(isPlaying);
        return ps;
    }

    public static PlayerSet createWithTheseCards(String playerName, int[] values){
        return createWithTheseCards(playerName, values, true);
    }

    public static PlayerSet createWithTheseCards(String playerName, int[] values, boolean visible){
        List<CardColumn> columnList = new ArrayList<CardColumn>();
        int valueIndex = 0;
        for (int col = 0; col < 4; col++){
            List<TableCard> cards = new ArrayList<TableCard>();
            for (int row = 0; row < 3; row++) {
                int value = valueIndex >= values.length ? 0 : values[valueIndex++];
                Card card = new Card(value, visible);
                TableCard tCard = new TableCard(row, card);
                cards.add(tCard);
            }
            columnList.add(new CardColumn(col, cards));
        }
        Player player = new Player();
        player.setName(playerName);
        player.setServiceAddress(String.format("http://localhost/%s", playerName.toLowerCase()));
        PlayerSet ps = new PlayerSet(columnList, player);
        ps.setPlaying(true);
        return ps;
    }
}
