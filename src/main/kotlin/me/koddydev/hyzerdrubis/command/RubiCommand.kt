package me.koddydev.hyzerdrubis.command

import me.koddydev.hyzerdrubis.api.rubis
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class RubiCommand : CommandManager("rubis") {

    init {
        usage = "/rubi"
        permission = "hyzerdrubis.cmd.rubis"
        register(LojaCommand())
        register(AdminCommand())
        register(EnviarCommand())
        register(HelpCommand())
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if(args.isEmpty()) {
            player.sendMessage("§aVocê possue §5${player.rubis.rubis.format(true)} rubis§a.")
        } else if(args[0] != "admin"){
            val target = Bukkit.getPlayer(args[0]) ?: return player.sendMessage("§aEste jogador está offline.")
            player.sendMessage("§aO jogador §7${target.displayName} §apossue §5${player.rubis.rubis.format(true)} rubis§a.")
        }
    }

}