package me.koddydev.hyzerdrubis

import com.mikael.mkAPI.api.MKPluginSystem
import com.mikael.mkAPI.spigot.api.apimanager
import me.koddydev.hyzerdrubis.api.PlaceHolder
import me.koddydev.hyzerdrubis.command.RubiCommand
import me.koddydev.hyzerdrubis.core.RubisSystem
import me.koddydev.hyzerdrubis.listener.GeneralListener
import me.koddydev.hyzerdrubis.`object`.PlayerData
import net.eduard.api.lib.config.Config
import net.eduard.api.lib.modules.BukkitTimeHandler
import net.eduard.api.lib.plugin.IPluginInstance
import net.eduard.api.lib.storage.StorageAPI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class RubisMain : JavaPlugin(), IPluginInstance, BukkitTimeHandler {

    companion object {
        lateinit var instance: RubisMain
    }

    lateinit var config: Config

    override fun onEnable() {
        instance = this@RubisMain
        val start = System.currentTimeMillis()
        log("§bCopyright Koddy#2884 2022")
        log("§eIniciando carregamento...")
        MKPluginSystem.loadedMKPlugins.add(this@RubisMain)

        log("§eCarregando diretórios...")
        config = Config(this@RubisMain, "config.yml")
        config.saveConfig()
        reloadConfigs() // x1
        reloadConfigs() // x2
        StorageAPI.updateReferences()

        log("§eCriando tabelas e referências...")

        apimanager.sqlManager.createTable<PlayerData>()
        apimanager.sqlManager.createReferences<PlayerData>()

        log("§eCarregando sistemas...")

        RubisSystem.onLoad()

        // Commands
        RubiCommand().registerCommand(this)

        // Listeners
        GeneralListener().registerListener(this)
        PlaceHolder().register()

        val endTime = System.currentTimeMillis() - start
        log("§aPlugin ativado! (Tempo de carregamento: §f${endTime}ms§a)")
        log("§bCopyright Koddy#2884 2022")
    }

    fun reloadConfigs() {

        config.add("Blocks.Stone.Enabled", true)
        config.add("Blocks.Stone.Name", Material.STONE, "Veja os nomes em https://helpch.at/docs/1.8/org/bukkit/Material.html")
        config.add("Blocks.Stone.Data", 0)
        config.add("Blocks.Stone.Give",  10)

        config.add("Categories.Picaretas.Name", "&fPicaretas", "Nome da Categoria")
        config.add("Categories.Picaretas.Lore", listOf("Nada de mais como descrição"))
        config.add("Categories.Picaretas.Material", Material.DIAMOND_PICKAXE)
        config.add("Categories.Picaretas.Data", 0)
        config.add("Categories.Picaretas.Glow", true)

        config.add("Categories.Picaretas.Items.Um.Name", "&ePicareta Suprema")
        config.add("Categories.Picaretas.Items.Um.Lore", listOf("&bPreco: 1k de gemas", "&f", "&6Encantamentos: ", "&bFortuna 35", "&cEficiência 15"))
        config.add("Categories.Picaretas.Items.Um.Price", 5000)
        config.add("Categories.Picaretas.Items.Um.Material", Material.DIAMOND_PICKAXE)
        config.add("Categories.Picaretas.Items.Um.Data", 0)
        config.add("Categories.Picaretas.Items.Um.Size", 1, "Quantidade de Items que será dada ao usuário caso o campo isCommand seja false.")
        config.add("Categories.Picaretas.Items.Um.Glow", true)
        config.add("Categories.Picaretas.Items.Um.isCommand", false)
        config.add("Categories.Picaretas.Items.Um.Command", "/give %player% minecraft:diamond_pickaxe 1")
        config.add("Categories.Picaretas.Items.Um.Enchantments", "${Enchantment.DIG_SPEED.name}=15;${Enchantment.LOOT_BONUS_BLOCKS.name}=35", "Caso o campo isCommand seja false, o item terá esses encantamentos, confira a lista em https://helpch.at/docs/1.8/index.html?org/bukkit/enchantments/Enchantment.html")
        config.saveConfig()
    }

    fun log(msg: String) {
        Bukkit.getConsoleSender().sendMessage("§b[${systemName}] §f$msg")
    }

    override fun getPlugin(): Any {
        return this
    }

    override fun getSystemName(): String {
        return this.name
    }

    override fun getPluginFolder(): File {
        return this.dataFolder
    }

    override fun getPluginConnected(): Plugin {
        return this
    }
}