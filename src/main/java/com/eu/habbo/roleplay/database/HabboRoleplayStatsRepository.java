package com.eu.habbo.roleplay.database;

import com.eu.habbo.Emulator;
import com.eu.habbo.roleplay.users.HabboRoleplayStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabboRoleplayStatsRepository {
    private static HabboRoleplayStatsRepository instance;

    public static HabboRoleplayStatsRepository getInstance() {
        if (instance == null) {
            instance = new HabboRoleplayStatsRepository();
        }
        return instance;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HabboRoleplayStatsRepository.class);

    public HabboRoleplayStats getByUserID(int userID) {
        String sqlSelect = "SELECT * FROM rp_users_stats WHERE user_id = ?";
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {

            selectStatement.setInt(1, userID);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new HabboRoleplayStats(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
        return null;
    }

    public List<HabboRoleplayStats> getByCorpID(int corpID) {
        String sqlSelect = "SELECT * FROM rp_users_stats WHERE corporation_id = ?";
        List<HabboRoleplayStats> statsList = new ArrayList<>();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {

            selectStatement.setInt(1, corpID);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    statsList.add(new HabboRoleplayStats(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
        return statsList;
    }

    public List<HabboRoleplayStats> getByCorpAndPositionID(int corpID, int positionID) {
        String sqlSelect = "SELECT * FROM rp_users_stats WHERE corporation_id = ? AND corporation_position_id = ?";
        List<HabboRoleplayStats> statsList = new ArrayList<>();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {

            selectStatement.setInt(1, corpID);
            selectStatement.setInt(2, positionID);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    statsList.add(new HabboRoleplayStats(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
        return statsList;
    }

    public HabboRoleplayStats create(HabboRoleplayStats habboRoleplayStats) {
        return this.create(
                habboRoleplayStats.getUserID(),
                habboRoleplayStats.getHealthNow(),
                habboRoleplayStats.getHealthMax(),
                habboRoleplayStats.getEnergyNow(),
                habboRoleplayStats.getEnergyMax(),
                habboRoleplayStats.getHungerNow(),
                habboRoleplayStats.getHungerMax(),
                habboRoleplayStats.getCorp().getGuild().getId(),
                habboRoleplayStats.getCorpPosition().getId(),
                habboRoleplayStats.getGang() != null ? habboRoleplayStats.getGang().getId() : null,
                habboRoleplayStats.getLastPosX(),
                habboRoleplayStats.getLastPosY(),
                habboRoleplayStats.getPlayerXP(),
                habboRoleplayStats.getStrengthXP(),
                habboRoleplayStats.getAccuracyXP(),
                habboRoleplayStats.getStaminaXP(),
                habboRoleplayStats.getMeleeXP(),
                habboRoleplayStats.getWeaponXP(),
                habboRoleplayStats.getFarmingXP(),
                habboRoleplayStats.getMiningXP(),
                habboRoleplayStats.getFishingXP()
        );
    }

    public HabboRoleplayStats create(int userID, int healthNow, int healthMax, int energyNow, int energyMax, int hungerNow, int hungerMax, int corporationID, int corporationPositionID, int gangID, int lastPosX, int lastPosY, int playerXP, int strengthXP, int accuracyXP, int staminaXP, int meleeXP, int weaponXP, int farmingXP, int miningXP, int fishingXP) {
        String sqlInsert = "INSERT INTO rp_users_stats (user_id, health_now, health_max, energy_now, energy_max, hunger_now, hunger_max, armor_now, armor_max, corporation_id, corporation_position_id, gang_id, last_pos_x, last_pos_y, player_xp, strength_xp, accuracy_xp, stamina_xp, melee_xp, weapon_xp, farming_xp, mining_xp, fishing_xp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, userID);
            statement.setInt(2, healthNow);
            statement.setInt(3, healthMax);
            statement.setInt(4, energyNow);
            statement.setInt(5, energyMax);
            statement.setInt(6, hungerNow);
            statement.setInt(7, hungerMax);
            statement.setInt(8, corporationID);
            statement.setInt(9, corporationPositionID);
            statement.setInt(10, gangID);
            statement.setInt(11, lastPosX);
            statement.setInt(12, lastPosY);
            statement.setInt(13, playerXP);
            statement.setInt(14, strengthXP);
            statement.setInt(15, accuracyXP);
            statement.setInt(16, staminaXP);
            statement.setInt(17, meleeXP);
            statement.setInt(18, weaponXP);
            statement.setInt(19, farmingXP);
            statement.setInt(20, miningXP);
            statement.setInt(21, fishingXP);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getByUserID(userID);
                } else {
                    throw new SQLException("Creating billing statement failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
            return null;
        }
    }

    public void update(HabboRoleplayStats habboRoleplayStats) {
        String sqlUpdate = "UPDATE rp_users_stats SET health_now = ?, health_max = ?, energy_now = ?, energy_max = ?, hunger_now = ?, hunger_max = ?, armor_now = ?, armor_max = ?, corporation_id = ?, corporation_position_id = ?, gang_id = ?, last_pos_x = ?, last_pos_y = ?, player_xp = ?, strength_xp = ?, accuracy_xp = ?, stamina_xp = ?, melee_xp = ?, weapon_xp = ?, farming_xp = ?, mining_xp = ?, fishing_xp = ? WHERE user_id = ?";
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {

            statement.setInt(1, habboRoleplayStats.getHealthNow());
            statement.setInt(2, habboRoleplayStats.getHealthMax());
            statement.setInt(3, habboRoleplayStats.getEnergyNow());
            statement.setInt(4, habboRoleplayStats.getEnergyMax());
            statement.setInt(5, habboRoleplayStats.getArmorNow());
            statement.setInt(6, habboRoleplayStats.getArmorMax());
            statement.setInt(7, habboRoleplayStats.getHungerNow());
            statement.setInt(8, habboRoleplayStats.getHungerMax());
            statement.setInt(9, habboRoleplayStats.getCorp().getGuild().getId());
            statement.setInt(10, habboRoleplayStats.getCorpPosition().getId());
            if (habboRoleplayStats.getGang() != null) {
                statement.setInt(11,habboRoleplayStats.getGang().getId());
            } else {
                statement.setNull(11, java.sql.Types.INTEGER);
            }
            statement.setInt(12, habboRoleplayStats.getLastPosX());
            statement.setInt(13, habboRoleplayStats.getLastPosY());
            statement.setInt(14, habboRoleplayStats.getPlayerXP());
            statement.setInt(15, habboRoleplayStats.getStrengthXP());
            statement.setInt(16, habboRoleplayStats.getAccuracyXP());
            statement.setInt(17, habboRoleplayStats.getStaminaXP());
            statement.setInt(18, habboRoleplayStats.getMeleeXP());
            statement.setInt(19, habboRoleplayStats.getWeaponXP());
            statement.setInt(20, habboRoleplayStats.getFarmingXP());
            statement.setInt(21, habboRoleplayStats.getMiningXP());
            statement.setInt(22, habboRoleplayStats.getFishingXP());

            statement.setInt(23, habboRoleplayStats.getUserID());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
    }

    public void delete(int id) {
        String sqlDelete = "DELETE FROM rp_users_bills WHERE id = ?";
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDelete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
    }
}
