package me.koddydev.hyzerdrubis.command

import com.mikael.mkAPI.utils.soundYes
import me.koddydev.hyzerdrubis.menu.LojaMenu
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player

class LojaCommand : CommandManager("loja") {

    init {
        usage = "/loja"
        permission = "hyzerdrubis.cmd.loja"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.soundYes(3f, 1f)
        LojaMenu.getMenu(player).update()
        LojaMenu.getMenu(player).open(player)
    }

}