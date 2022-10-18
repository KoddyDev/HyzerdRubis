package me.koddydev.hyzerdrubis.core

import me.koddydev.hyzerdrubis.RubisMain
import me.koddydev.hyzerdrubis.`object`.BlocksObject
import me.koddydev.hyzerdrubis.`object`.CategoryObject
import me.koddydev.hyzerdrubis.`object`.ItemObject
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.storage.StorageAPI
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag

object RubisSystem {

    val categories = mutableListOf<CategoryObject>()
    val blocks = mutableListOf<BlocksObject>()

    fun onLoad() {
        val categoryConfig = RubisMain.instance.config.getSection("Categories")
        val blocksConfig = RubisMain.instance.config.getSection("Blocks")

        categoryConfig.keys.forEach { key ->
            val name = RubisMain.instance.config.getString("Categories.${key}.Name").replace("&", "§")
            val lore = mutableListOf<String>()
            val material =  RubisMain.instance.config["Categories.${key}.Material", Material::class.java]
            val data = RubisMain.instance.config.getInt("Categories.${key}.Data")
            val glow = RubisMain.instance.config.getBoolean("Categories.${key}.Glow")
            val items = RubisMain.instance.config.getSection("Categories.${key}.Items")
            val itemsSet = mutableListOf<ItemObject>();

            RubisMain.instance.config.getStringList("Categories.${key}.Lore").forEach {
                lore.add(it.replace("&", "§"))
            }

            val item = ItemBuilder(material)
                .data(data)
                .name(name)
                .lore(lore)

            if(glow) item.glowed()

            val category = CategoryObject(name, lore, item, itemsSet)

            items.keys.forEach { itemKey ->
                val itemName = RubisMain.instance.config.getString("Categories.${key}.Items.${itemKey}.Name").replace("&", "§")
                val itemLore =  mutableListOf<String>()
                val itemPrice = RubisMain.instance.config.getInt("Categories.${key}.Items.${itemKey}.Price")
                val glowed = RubisMain.instance.config.getBoolean("Categories.${key}.Items.${itemKey}.Glowed")
                val isCommand = RubisMain.instance.config.getBoolean("Categories.${key}.Items.${itemKey}.isCommand")
                val command = RubisMain.instance.config.getString("Categories.${key}.Items.${itemKey}.Command")
                val materialC = RubisMain.instance.config["Categories.${key}.Items.${itemKey}.Material", Material::class.java]
                val dataC = RubisMain.instance.config.getInt("Categories.${key}.Items.${itemKey}.Data")
                RubisMain.instance.config.getStringList("Categories.${key}.Items.${itemKey}.Lore").forEach {
                    itemLore.add(it.replace("&", "§"))
                }
                val enchantmentsList = RubisMain.instance.config.getString("Categories.${key}.Items.${itemKey}.Enchantments")
                val enchantments = mutableMapOf<Enchantment, Int>()
                val size = RubisMain.instance.config.getInt("Categories.${key}.Items.${itemKey}.Size")
                enchantmentsList.split(";").forEach { enchant ->
                    val item1 = enchant.split("=")

                    if(item1[0].isNotEmpty()) {
                        enchantments[Enchantment.getByName(item1[0])] = item1[1].toInt()
                    }
                }

                RubisMain.instance.log("$itemName $dataC $size")

                val item2 = ItemBuilder(materialC, size)
                item2.data(dataC)
                item2.name(itemName)
                item2.lore(itemLore)

                enchantments.forEach { enchant ->
                    item2.addEnchant(enchant.key, enchant.value)
                }

                item2.addFlags(ItemFlag.HIDE_ENCHANTS)

                if(glowed) item2.glowed()

                itemsSet.add(ItemObject(itemName, itemLore, item2, itemPrice, category, isCommand, command, enchantments, size ))
            }

            category.items = itemsSet

            categories.add(category)
        }

        blocksConfig.keys.forEach {
            val enabled = RubisMain.instance.config.getBoolean("Blocks.${it}.Enabled")
            val name = RubisMain.instance.config["Blocks.${it}.Name", Material::class.java]
            val data = RubisMain.instance.config.getInt("Blocks.${it}.Data")
            val give = RubisMain.instance.config.getInt("Blocks.${it}.Give")
            val item = ItemBuilder(name)
            item.data(data)

            val block = BlocksObject(enabled, name, item, give, data)

            blocks.add(block)
        }
    }
    fun reload() {
        categories.clear()
        blocks.clear()
        RubisMain.instance.config.saveConfig()
        StorageAPI.updateReferences()
        onLoad()
    }

    init {
        categories.clear()
        blocks.clear()
    }
}