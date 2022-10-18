package me.koddydev.hyzerdrubis.`object`

import com.mikael.mkAPI.api.MKPluginData
import com.mikael.mkAPI.spigot.api.apimanager
import net.eduard.api.lib.database.annotations.ColumnPrimary
import net.eduard.api.lib.database.annotations.ColumnUnique
import net.eduard.api.lib.database.annotations.TableName

@TableName("hyzerdrubis_players")
class PlayerData : MKPluginData {

    @ColumnPrimary
    var id = 0L // L serve para passar to long = 0.toLong()

    @ColumnUnique
    var player = "PlayerName"

    var rubis = 0

    fun addRubis(value: Int): Boolean {
        return try {
            rubis += value
            update()
            true // Simples
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun remRubis(value: Int): Boolean {
        return try {
            rubis -= value
            update()
            true // Simples
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun setRubis(value: Int): Boolean {
        return try {
            rubis = value
            update()
            true // Simples
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    companion object {
        val cache = mutableMapOf<String, PlayerData>()

        fun loadData(playerName: String): PlayerData {
            if(cache.containsKey(playerName.lowercase())) {
                return cache[playerName.lowercase()]!!
            }
            var data = apimanager.sqlManager.getData(PlayerData::class.java, "player", playerName.lowercase())
            if (data == null) {
                data = PlayerData()
                data.player = playerName.lowercase()
                data.insert()
            }

            cache[playerName.lowercase()] = data
            return data
        }
    }
}