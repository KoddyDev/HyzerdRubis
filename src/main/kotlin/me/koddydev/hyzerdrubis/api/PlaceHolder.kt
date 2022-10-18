package me.koddydev.hyzerdrubis.api

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import net.eduard.api.lib.kotlin.format
import org.bukkit.entity.Player


class PlaceHolder : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "hyzerdrubis"
    }

    override fun getAuthor(): String {
        return "KoddyDev"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onPlaceholderRequest(p: Player?, identifier: String): String? {
        return if (identifier == "rubis") {
            if(p == null) return "0"
            return p.rubis.rubis.format(true)
        } else null
    }
}