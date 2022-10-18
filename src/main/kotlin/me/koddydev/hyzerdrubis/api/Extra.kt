package me.koddydev.hyzerdrubis.api

import me.koddydev.hyzerdrubis.`object`.PlayerData
import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

val Player.rubis get() = PlayerData.cache[this.name.lowercase()]!!