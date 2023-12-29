package com.skyjo.application.dto.play.result;

import java.util.List;

import com.skyjo.core.models.Card;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CardChangeResult extends CardTurnResult{

    protected Card discard;

    public CardChangeResult() {
        super();
    }

    public CardChangeResult(List<Card> removedColumn, Card discard) {
        super(removedColumn);
        this.discard = discard;
    }

    @Override
    public List<Card> getPlayedCards(){
        return removedColumn;
    }

    @Override
    public Card getDisCard()
    {
        return discard;
    }
}
