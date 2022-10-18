package me.koddydev.hyzerdrubis.command

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player

class AdminCommand : CommandManager("admin") {

    init {
        usage = "/admin"
        permission = "hyzerdrubis.admin"
        register(AddCommand())
        register(RemCommand())
        register(SetCommand())
        register(ReloadCommand())
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.sendMessage("§f")
        player.sendMessage("§fLista de Comandos Administrativos")
        player.sendMessage("§f")
        player.sendMessage("§b/rubis admin add <player> <quantia>§7 - Adiciona Rubis á um player")
        player.sendMessage("§b/rubis admin set <player> <quantia>§7 - Seta Rubis á um player")
        player.sendMessage("§b/rubis admin rem <player> <quantia>§7 - Remove Rubis de um player")
        player.sendMessage("§b/rubis admin reload§7 - Recarrega o sistema de Rubis")
        player.sendMessage("§f")
    }

}