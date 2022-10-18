package me.koddydev.hyzerdrubis.command

import me.koddydev.hyzerdrubis.api.rubis
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class EnviarCommand : CommandManager("enviar") {

    init {
        usage = "/rubis enviar <player> <quantia>"
        permission = "hyzerdrubis.cmd.enviar"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if(args.size != 2) {
            return sendUsage(player)
        }

        val target = Bukkit.getPlayer(args[0]) ?: return player.sendMessage("§cEste jogador está offline.")
        val quantia = args[1]

        if(!isNumber(quantia)) {
            return player.sendMessage("§cVocê deve inserir um numero na quantia.")
        }

        if(player.rubis.rubis < quantia.toInt()) {
            return player.sendMessage("§cO jogador não possue §5${quantia.toInt().format(true)} rubis§c.")
        }

        target.rubis.addRubis(quantia.toInt())
        player.rubis.remRubis(quantia.toInt())

        player.sendMessage("§aVocê enviou §5${quantia.toInt().format(true)} rubis §apara o jogador jogador §f${args[0]}")

    }

    fun isNumber(number: String): Boolean {
        return try {
            number.toInt()
            true
        } catch (e: Exception) {
            false
        }
    }
}