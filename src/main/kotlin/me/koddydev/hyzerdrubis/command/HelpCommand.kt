package me.koddydev.hyzerdrubis.command

import me.koddydev.hyzerdrubis.api.rubis
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class HelpCommand : CommandManager("ajuda", "help") {

    init {
        usage = "/rubis ajuda"
        permission = "hyzerdrubis.cmd.ajuda"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.sendMessage("§f")
        player.sendMessage("§fLista de Comandos")
        player.sendMessage("§f")
        player.sendMessage("§b/rubis <player>§7 - Veja a quantidade de rubis de um membro")
        player.sendMessage("§b/rubis loja§7 - Visualize a loja de rubis")
        player.sendMessage("§b/rubis enviar <player> <quantia> §7- Envie uma quantidade de rubis á um player")
        if(player.hasPermission("hyzerdrubis.admin")) {
            player.sendMessage("§c/rubis admin add <player> <quantia>§7 - Adiciona Rubis á um player")
            player.sendMessage("§c/rubis admin set <player> <quantia>§7 - Seta Rubis á um player")
            player.sendMessage("§c/rubis admin rem <player> <quantia>§7 - Remove Rubis de um player")
        }
        player.sendMessage("§f")
    }
}