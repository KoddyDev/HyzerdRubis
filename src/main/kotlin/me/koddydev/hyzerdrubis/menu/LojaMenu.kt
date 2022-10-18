package me.koddydev.hyzerdrubis.menu

import me.koddydev.hyzerdrubis.RubisMain
import me.koddydev.hyzerdrubis.core.RubisSystem
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemFlag

class LojaMenu(var player: Player) : Menu("Loja de Rubis", 6) {
    companion object {
        lateinit var instance: LojaMenu

        private val menus = mutableMapOf<Player, LojaMenu>()

        fun getMenu(player: Player): LojaMenu {
            if (menus.containsKey(player)) return menus[player]!!
            val playerMenu = LojaMenu(player)
            menus[player] = playerMenu
            playerMenu.registerMenu(RubisMain.instance)
            return playerMenu
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(e: PlayerQuitEvent) {
        if (e.player == player) {
            unregisterMenu()
            menus.remove(e.player)
        }
    }

    init {
        instance = this@LojaMenu
        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 6)
        autoAlignSkipColumns = listOf(9,8,2,1)
        autoAlignPerLine = 5
        autoAlignPerPage = 5 * autoAlignPerLine
        update()
    }

    override fun update() {
        removeAllButtons()

        RubisSystem.categories.forEach { category ->
            button("btn-${category.name}") {
                iconPerPlayer = {
                    category.item.clone()
                }

                click = ClickEffect {
                    val player = it.player
                    player.closeInventory()
                    LojaMenuCategory.getMenu(player, category).open(player)
                }
            }
        }
    }

}