package me.koddydev.hyzerdrubis.`object`

import net.eduard.api.lib.game.ItemBuilder

class CategoryObject (
    val name: String,
    val lore: MutableList<String>,
    val item: ItemBuilder,
    var items: MutableList<ItemObject>
        )