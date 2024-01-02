package com.skyjo.application.dto.protocol;

import com.skyjo.application.dto.Location;
import lombok.Data;

@Data
public class FlipResult {
    protected Location location;
    protected boolean success;

    public FlipResult() {
        this.location = new Location();
        this.success = false;
    }

    public FlipResult(Location location, boolean isSuccess) {
        this.location = location;
        this.success = isSuccess;
    }
}
