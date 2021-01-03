package io.bini.poker.pokerdujeudi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddSessionDTO {
    private long[] playerIds;
    private long placeId;
    private long seasonId;
    private Date date;
}
