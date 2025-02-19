package com.eu.habbo.habbohotel.items.interactions.wired.triggers;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.wired.WiredTriggerType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredTriggerTeamLoses extends WiredTriggerGameStarts {
    public WiredTriggerTeamLoses(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredTriggerTeamLoses(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public WiredTriggerType getType() {
        return WiredTriggerType.CUSTOM;
    }
}