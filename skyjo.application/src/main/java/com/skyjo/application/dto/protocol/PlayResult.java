package com.skyjo.application.dto.protocol;

import com.skyjo.application.dto.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlayResult extends FlipResult{
    
    protected boolean keepTheCard;

    public PlayResult() {
        super();
        keepTheCard = false;
    }

    public PlayResult(Location location, boolean isSuccess, boolean keepTheCard) {
        super(location, isSuccess);
        this.keepTheCard = keepTheCard;
    }
}
