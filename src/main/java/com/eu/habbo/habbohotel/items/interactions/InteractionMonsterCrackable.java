package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.ICycleable;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.HabboInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionMonsterCrackable extends InteractionCrackable implements ICycleable {
    private int lastHealthChange = 0;
    private boolean respawn = false;

    public InteractionMonsterCrackable(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionMonsterCrackable(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void cycle(Room room) {
        if (this.ticks > 0 && Emulator.getIntUnixTimestamp() - this.lastHealthChange > 30) {
            this.lastHealthChange = Emulator.getIntUnixTimestamp();
            this.ticks--;
            room.updateItem(this);
        }
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        if (room.getRoomInfo().isPublicRoom()) this.respawn = true;

        super.onClick(client, room, objects);
    }

    @Override
    public boolean resetable() {
        return this.respawn;
    }

    @Override
    public void reset(Room room) {
        RoomTile tile = room.getRandomWalkableTile();

        this.setCurrentPosition(tile);
        this.setCurrentZ(room.getStackHeight(tile.getX(), tile.getY(), false));

        super.reset(room);
    }

    @Override
    public boolean allowAnyone() {
        return this.respawn;
    }

    @Override
    public boolean isUsable() {
        return true;
    }

    @Override
    protected boolean placeInRoom() {
        return this.respawn;
    }
}