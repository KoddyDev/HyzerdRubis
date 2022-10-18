package me.koddydev.hyzerdrubis.menu

import com.mikael.mkAPI.utils.soundNo
import me.koddydev.hyzerdrubis.RubisMain
import me.koddydev.hyzerdrubis.api.rubis
import me.koddydev.hyzerdrubis.core.RubisSystem
import me.koddydev.hyzerdrubis.`object`.CategoryObject
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent

class LojaMenuCategory(var player: Player, var category: CategoryObject) : Menu("Loja de Rubis", 6) {
    companion object {
        lateinit var instance: LojaMenuCategory
        private val menus = mutableMapOf<Player, LojaMenuCategory>()

        fun getMenu(player: Player, category: CategoryObject): LojaMenuCategory {
            if (menus.containsKey(player)) return menus[player]!!
            val playerMenu = LojaMenuCategory(player, category)
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
        instance = this@LojaMenuCategory
        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 2, 5, 6)
        autoAlignSkipColumns = listOf(9,8,2,1)
        autoAlignPerLine = 5
        autoAlignPerPage = 5 * autoAlignPerLine
        update()
    }

    override fun update() {
        removeAllButtons()

        button("Back") {
            iconPerPlayer = {
                setPosition(1, 6)

                ItemBuilder(Material.ARROW)
                    .name("§aVoltar")
                    .lore("§7", "§7Clique aqui para voltar ao menu anterior", "§f")
                    .glowed()
            }

            click = ClickEffect {
                LojaMenu.getMenu(it.player).update()
                LojaMenu.getMenu(it.player).open(it.player)
            }
        }

        nextPage.item = ItemBuilder(Material.INK_SACK)
            .data(10)
            .name("§aAvançar Pagina")
            .lore("§f", "§7Clique aqui para ir para a pagina §f%page")
        nextPage.setPosition(9, 3)
        nextPageSound = SoundEffect(Sound.NOTE_PLING, 2f, 1f)

        previousPage.item = ItemBuilder(Material.INK_SACK)
            .data(1)
            .name("§aAvançar Pagina")
            .lore("§f", "§7Clique aqui para ir para a pagina §f%page")
        previousPageSound = SoundEffect(Sound.NOTE_PLING, 2f, 1f)
        previousPage.setPosition(1, 3)

        category.items.forEach { item ->
            button("btn-${item.name}") {
                iconPerPlayer = {
                    item.item.clone()
                }

                click = ClickEffect {
                    val player = it.player

                    player.closeInventory()

                    if(player.rubis.rubis < item.price) {
                        player.soundNo(2f, 1f)
                        player.sendMessage("§cVocê não possue §5${item.price} rubis §cpara comprar este item.")
                        return@ClickEffect
                    }
                    if(player.inventory.contents.filter { it != null }.size == 36) {
                        return@ClickEffect player.sendMessage("§cVocê não possue espaço disponivel em seu inventario.")
                    }
                    player.rubis.remRubis(item.price)
                    if(item.isCommand) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.command.replace("/", ""))
                    } else {
                        player.inventory.addItem(item.item)
                    }

                    player.sendMessage("§aVocê comprou §f${item.name}§a por §5${item.price} gemas§a.")
                }
            }
        }
    }

}