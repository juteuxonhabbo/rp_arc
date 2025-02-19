package com.eu.habbo.roleplay.commands.bank;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.roleplay.corp.Corp;
import com.eu.habbo.roleplay.corp.CorpManager;
import com.eu.habbo.roleplay.database.HabboBankAccountRepository;
import com.eu.habbo.roleplay.users.HabboBankAccount;

public class BankAccountDepositCommand extends Command  {

    public BankAccountDepositCommand() {
        super("cmd_bank_deposit");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {

        if (params == null) {
            return true;
        }

        if (params.length != 3) {
            return true;
        }

        int corpID = Integer.parseInt(params[1]);
        Corp bankCorp = CorpManager.getInstance().getCorpByID(corpID);

        if (bankCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.corp_not_found"));
            return true;
        }

        HabboBankAccount bankAccount = HabboBankAccountRepository.getInstance().getByUserAndCorpID(gameClient.getHabbo().getHabboInfo().getId(), corpID);

        if (bankAccount == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.bank.account_not_found"));
            return true;
        }

        int depositAmount = Integer.parseInt(params[2]);

        if (gameClient.getHabbo().getHabboInfo().getCredits() < depositAmount) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.not_enough_credits"));
            return true;
        }

        gameClient.getHabbo().getHabboInfo().setCredits(gameClient.getHabbo().getHabboInfo().getCredits() - depositAmount);
        bankAccount.setCheckingBalance(bankAccount.getCheckingBalance() + depositAmount);
        HabboBankAccountRepository.getInstance().update(bankAccount);

        gameClient.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.bank.deposit_success")
                .replace(":credits", String.valueOf(depositAmount))
        );

        return true;
    }
}
