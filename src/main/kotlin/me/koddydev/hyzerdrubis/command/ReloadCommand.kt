package me.koddydev.hyzerdrubis.command

import me.koddydev.hyzerdrubis.api.rubis
import me.koddydev.hyzerdrubis.core.RubisSystem
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ReloadCommand : CommandManager("reload") {

    init {
        usage = "/rubis admin reload"
        permission = "hyzerdrubis.admin"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.sendMessage("§cRecarregando sistemas...")
        RubisSystem.reload()
        player.sendMessage("§aSistemas recarregados com sucesso!")
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