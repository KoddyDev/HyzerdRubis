package me.koddydev.hyzerdrubis.`object`

import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material

class BlocksObject (
    val enabled: Boolean,
    val material: Material,
    val item: ItemBuilder,
    val give: Int,
    val data: Int
)