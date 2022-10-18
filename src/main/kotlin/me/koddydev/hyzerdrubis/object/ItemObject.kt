package me.koddydev.hyzerdrubis.`object`

import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.enchantments.Enchantment

class ItemObject(
    val name: String,
    val lore: MutableList<String>,
    val item: ItemBuilder,
    val price: Int,
    val category: CategoryObject,
    val isCommand: Boolean,
    val command: String = "",
    val enchants: MutableMap<Enchantment, Int>,
    val size: Int
    )