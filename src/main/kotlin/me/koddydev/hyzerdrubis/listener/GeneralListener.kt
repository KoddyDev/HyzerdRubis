package me.koddydev.hyzerdrubis.listener

import me.koddydev.hyzerdrubis.api.rubis
import me.koddydev.hyzerdrubis.core.RubisSystem
import me.koddydev.hyzerdrubis.`object`.PlayerData
import net.eduard.api.lib.manager.EventsManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

@Suppress("DEPRECATION")
class GeneralListener : EventsManager() {

    @EventHandler(priority = EventPriority.LOW)
    fun onPreLogin(e: AsyncPlayerPreLoginEvent) {
        try {
            PlayerData.loadData(e.name)
        } catch (ex: Exception) {
            ex.printStackTrace()
            e.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                "§cOcorreu um erro ao carregar seus dados de spawner."
            )
            e.kickMessage = "§cOcorreu um erro ao carregar seus dados de spawner."
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(e: PlayerJoinEvent) {
        e.joinMessage = null
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(e: PlayerQuitEvent) {
        e.quitMessage = null
        val player = e.player

        // Remover player do acche
        PlayerData.cache.remove(player.name.lowercase())
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBreak(e: BlockBreakEvent) {
        val block = e.block
        val findB = RubisSystem.blocks.find { it.item.type == block.type && it.data.toByte() == block.data} ?: return
        val player = e.player

        player.rubis.addRubis(findB.give)
    }
}