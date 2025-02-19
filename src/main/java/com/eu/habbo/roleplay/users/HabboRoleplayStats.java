package com.eu.habbo.roleplay.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.habbohotel.guilds.GuildMember;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.roleplay.actions.HospitalRecoveryAction;
import com.eu.habbo.roleplay.actions.TeleportHospitalAction;
import com.eu.habbo.roleplay.corp.Corp;
import com.eu.habbo.roleplay.corp.CorpManager;
import com.eu.habbo.roleplay.corp.CorpPosition;
import com.eu.habbo.roleplay.skill.*;
import com.eu.habbo.roleplay.messages.outgoing.user.UserRoleplayStatsChangeComposer;
import com.eu.habbo.roleplay.weapons.Weapon;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.*;

public class HabboRoleplayStats{
    @Getter
    private final int userID;
    @Getter
    private int healthNow;
    @Getter
    private int healthMax;
    @Getter
    private int energyNow;
    @Getter
    private int energyMax;
    @Getter
    private int hungerNow;
    @Getter
    private int hungerMax;
    @Getter
    private int armorNow;
    @Getter
    private int armorMax;
    private int corporationID;
    private int corporationPositionID;
    private Integer gangID;
    @Getter
    private boolean isDead;
    @Getter
    private boolean isStunned;
    @Getter
    private boolean isCuffed;
    @Setter
    @Getter
    private boolean isWorking;
    @Setter
    @Getter
    private boolean isJailed;
    @Getter
    private Habbo escortedBy;
    @Getter
    private Habbo isEscorting;
    @Getter
    private short lastPosX;
    @Getter
    private short lastPosY;

    @Setter
    @Getter
    private int playerXP;
    public void addPlayerXP(int xp) {
        int currentLevel = this.getPlayerLevel().getCurrentLevel();
        this.playerXP += xp;
        int updatedLevel = this.getPlayerLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getPlayerLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentLevel != updatedLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getPlayerLevel().getType())
                    .replace(":level", String.valueOf(updatedLevel))
            );
        }
    }
    public PlayerSkill getPlayerLevel() {
        return new PlayerSkill(this.playerXP, 1, 1);
    }

    @Getter
    private int strengthXP;
    public void addStrengthXP(int xp) {
        int currentStrengthLevel = this.getStrengthLevel().getCurrentLevel();
        this.strengthXP += xp;
        this.playerXP += xp;
        int updatedStrengthLevel = this.getStrengthLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getStrengthLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentStrengthLevel != updatedStrengthLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getStrengthLevel().getType())
                    .replace(":level", String.valueOf(updatedStrengthLevel))
            );
        }
    }
    public StrengthSkill getStrengthLevel() {
        return new StrengthSkill(this.strengthXP, 1, 1);
    }

    @Getter
    private int accuracyXP;
    public void addAccuracyXP(int xp) {
        int currentAccuracyLevel = this.getAccuracyLevel().getCurrentLevel();
        this.accuracyXP += xp;
        this.playerXP += xp;
        int updatedAccuracyLevel = this.getAccuracyLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getAccuracyLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentAccuracyLevel != updatedAccuracyLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getAccuracyLevel().getType())
                    .replace(":level", String.valueOf(updatedAccuracyLevel))
            );
        }
    }
    public AccuracySkill getAccuracyLevel() {
        return new AccuracySkill(this.accuracyXP, 1, 1);
    }

    @Getter
    private int staminaXP;
    public void addStaminaXP(int xp) {
        int currentStaminaLevel = this.getStaminaLevel().getCurrentLevel();
        this.staminaXP += xp;
        this.playerXP += xp;
        int updatedStaminaLevel = this.getStaminaLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getStaminaLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentStaminaLevel != updatedStaminaLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getStaminaLevel().getType())
                    .replace(":level", String.valueOf(updatedStaminaLevel))
            );
        }
    }
    public StaminaSkill getStaminaLevel() {
        return new StaminaSkill(this.staminaXP, 1, 1);
    }
    @Getter
    private int meleeXP;
    public void addMeleeXP(int xp) {
        int currentMeleeLevel = this.getMeleeLevel().getCurrentLevel();
        this.meleeXP += xp;
        this.playerXP += xp;
        int updatedMeleeLevel = this.getMeleeLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getMeleeLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentMeleeLevel != updatedMeleeLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedMeleeLevel))
            );
        }
    }
    public MeleeSkill getMeleeLevel() {
        return new MeleeSkill(this.meleeXP, 1, 1);
    }

    @Getter
    private int farmingXP;
    public void addFarmingXP(int xp) {
        int currentFarmingLevel = this.getFarmingLevel().getCurrentLevel();
        this.farmingXP += xp;
        this.playerXP += xp;
        int updatedFarmingLevel = this.getFarmingLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getFarmingLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentFarmingLevel != updatedFarmingLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedFarmingLevel))
            );
        }
    }
    public FarmingSkill getFarmingLevel() {
        return new FarmingSkill(this.farmingXP, 1, 1);
    }

    @Getter
    private int fishingXP;
    public void addFishingXP(int xp) {
        int currentFishingLevel = this.getFishingLevel().getCurrentLevel();
        this.fishingXP += xp;
        this.playerXP += xp;
        int updatedFishingLevel = this.getFishingLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getFishingLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentFishingLevel != updatedFishingLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedFishingLevel))
            );
        }
    }
    public FishingSkill getFishingLevel() {
        return new FishingSkill(this.fishingXP, 1, 1);
    }

    @Getter
    private int miningXP;
    public void addMiningXP(int xp) {
        int currentMiningLevel = this.getMiningLevel().getCurrentLevel();
        this.miningXP += xp;
        this.playerXP += xp;
        int updatedMiningLevel = this.getMiningLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getMiningLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentMiningLevel != updatedMiningLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedMiningLevel))
            );
        }
    }
    public MiningSkill getMiningLevel() {
        return new MiningSkill(this.miningXP, 1, 1);
    }

    @Getter
    private int lumberjackXP;
    public void addLumberjackXP(int xp) {
        int currentLumberjackLevel = this.getLumberjackLevel().getCurrentLevel();
        this.lumberjackXP += xp;
        this.playerXP += xp;
        int updatedLumberjackLevel = this.getLumberjackLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getLumberjackLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentLumberjackLevel != updatedLumberjackLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedLumberjackLevel))
            );
        }
    }
    public LumberjackSkill getLumberjackLevel() {
        return new LumberjackSkill(this.lumberjackXP, 1, 1);
    }

    @Getter
    private int weaponXP;
    public void addWeaponXP(int xp) {
        int currentWeaponLevel = this.getWeaponLevel().getCurrentLevel();
        this.weaponXP += xp;
        this.playerXP += xp;
        int updatedWeaponLevel = this.getWeaponLevel().getCurrentLevel();
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.xp.up")
                .replace(":skill", this.getWeaponLevel().getType())
                .replace(":xp", String.valueOf(xp))
        );
        if (currentWeaponLevel != updatedWeaponLevel) {
            this.getHabbo().shout(Emulator.getTexts()
                    .getValue("roleplay.level.up")
                    .replace(":skill", this.getMeleeLevel().getType())
                    .replace(":level", String.valueOf(updatedWeaponLevel))
            );
        }
    }
    public WeaponSkill getWeaponLevel() {
        return new WeaponSkill(this.weaponXP, 1, 1);
    }

    @Getter
    private long attackTimeoutExpiresAt;

    public void setAttackTimeoutSeconds(int seconds) {
        this.attackTimeoutExpiresAt = System.currentTimeMillis() + (seconds * 1000L);  // Convert seconds to milliseconds
    }

    public boolean getCombatBlocked() {
        return (System.currentTimeMillis() >=  this.attackTimeoutExpiresAt);
    }

    public int getCombatDelayRemaining() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = attackTimeoutExpiresAt - currentTime;
        return (int) Math.max(0, Math.ceil(timeElapsed/ 1000.0));
    }

    public void setHealth(int healthCurrent) {
        this.setHealth(healthCurrent, false);
    }

    public void setHealth(int currentHealth, boolean overrideMaxHealth) {
        this.healthNow = currentHealth;

        if (this.healthNow > this.healthMax && overrideMaxHealth) {
            this.healthMax = this.healthNow;
        }

        if (this.healthNow < 0) {
            this.healthNow = 0;
        }

        if (this.healthNow == 0) {
            this.setIsDead(true);
        }

        if (this.healthNow > 0 && this.isDead) {
            this.setIsDead(false);
        }
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }
    
    public Habbo getHabbo() {
        return Emulator.getGameEnvironment().getHabboManager().getHabbo(this.userID);
    }

    public void addEnergy(int energyGained, String action) {
        this.energyNow = (this.energyNow + energyGained) > this.energyMax ? this.energyMax : this.energyNow + energyGained;
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
        this.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.energy.gained")
                .replace(":action", action)
                .replace(":energy", String.valueOf(energyGained))
        );
    }

    public void depleteEnergy(int energyDepleted) {
        this.energyNow = this.energyNow < energyDepleted ? 0 : this.energyNow - energyDepleted;
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public Corp getCorp() {
        return CorpManager.getInstance().getCorpByID(this.corporationID);
    }

    public void setCorp(int corporationID, int corporationPositionID) {
        this.corporationID = corporationID;
        this.corporationPositionID = corporationPositionID;
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public CorpPosition getCorpPosition() {
        return this.getCorp().getPositionByID(this.corporationPositionID);
    }

    public Guild getGang() {
        if (this.gangID == null) {
            return null;
        }
        return Emulator.getGameEnvironment().getGuildManager().getGuild(this.gangID);
    }

    public void setGang(Integer gangID ) {
        this.gangID = gangID;
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public GuildMember getGangPosition() {
        return Emulator.getGameEnvironment().getGuildManager().getGuildMember(this.gangID, this.getHabbo().getHabboInfo().getId());
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;

        Emulator.getThreading().run(new HospitalRecoveryAction(this.getHabbo()));

        if (this.isDead) {
            Emulator.getThreading().run(new TeleportHospitalAction(this.getHabbo()));
        }

        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public void setIsStunned(boolean isStunned) {
        this.isStunned = isStunned;
        this.getHabbo().getRoomUnit().setCanWalk(!isStunned);
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public void setIsCuffed(boolean isCuffed) {
        this.isCuffed = isCuffed;
        if (!this.isStunned) this.getHabbo().getRoomUnit().setCanWalk(!isCuffed);
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public void setEscortedBy(Habbo escortedBy) {
        this.escortedBy = escortedBy;
        if (!this.isCuffed) this.getHabbo().getRoomUnit().setCanWalk(escortedBy != null);
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public void setIsEscorting(Habbo user) {
        Habbo oldUser = this.isEscorting;
        if (oldUser != null && oldUser != user) {
            oldUser.getHabboRoleplayStats().setEscortedBy(null);
        }
        if (oldUser == null && user == null) {
            this.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay_cmd_escort_stop"));
        }
        if (user != null) {
            this.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay_cmd_escort_start").replace(":username", user.getHabboInfo().getUsername()));
        }
        this.isEscorting = user;
        this.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.getHabbo()).compose());
    }

    public void setLastPos(short x, short y) {
        this.lastPosX = x;
        this.lastPosY = y;
    }

    public int getDamageModifier(HabboWeapon weapon) {
        Random random = new Random();
        int damageModifier = 0;

        if (weapon == null) {
            damageModifier = random.nextInt(2 * this.getStrengthLevel().getCurrentLevel()) + this.getMeleeLevel().getCurrentLevel();
        }

        if (weapon != null) {
            damageModifier = random.nextInt(2 * this.getAccuracyLevel().getCurrentLevel()) + this.getWeaponLevel().getCurrentLevel();
        }

        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.userID);

        if (habbo.getInventory().getWeaponsComponent().getEquippedWeapon() != null) {
            Weapon equippedWeapon = habbo.getInventory().getWeaponsComponent().getEquippedWeapon().getWeapon();
            damageModifier += random.nextInt(equippedWeapon.getMaxDamage() - equippedWeapon.getMinDamage() + equippedWeapon.getMaxDamage());
        }
        return damageModifier;
    }

    public HabboRoleplayStats(ResultSet set) throws SQLException {
        this.userID = set.getInt("user_id");
        this.isDead = set.getInt("health_now") <= 0;
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");
        this.hungerNow = set.getInt("hunger_now");
        this.hungerMax = set.getInt("hunger_max");
        this.armorNow = set.getInt("armor_now");
        this.armorMax = set.getInt("armor_max");
        this.corporationID = set.getInt("corporation_id");
        this.corporationPositionID = set.getInt("corporation_position_id");
        this.gangID = set.getInt("gang_id") != 0 ? set.getInt("gang_id") : null;
        this.lastPosX = set.getShort("last_pos_x");
        this.lastPosY = set.getShort("last_pos_y");
        this.playerXP = set.getInt("player_xp");
        this.staminaXP = set.getInt("stamina_xp");
        this.accuracyXP = set.getInt("accuracy_xp");
        this.meleeXP = set.getInt("melee_xp");
        this.farmingXP = set.getInt("farming_xp");
        this.fishingXP = set.getInt("fishing_xp");
        this.miningXP = set.getInt("mining_xp");
        this.weaponXP = set.getInt("weapon_xp");
    }
}