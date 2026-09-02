package me.Spielername124.blockClicker.CommandManagement.Commands;

import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    public static boolean help(CommandSender sender) {

        sender.sendMessage(Component.text("========== ", NamedTextColor.GOLD)
                .append(Component.text("BlockClicker Help", NamedTextColor.YELLOW))
                .append(Component.text(" ==========", NamedTextColor.GOLD)));

        sender.sendMessage(Component.text("/blc reload", NamedTextColor.YELLOW)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text("Reloads the plugin configuration.", NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/blc saveItem <customName> [maxStacksize]", NamedTextColor.YELLOW)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text("Saves the item held in your main hand. (Name must not match a material/tool type). ", NamedTextColor.WHITE))
                .append(Component.text("[maxStacksize] is optional, but if set, it overrides the max stack size (e.g. stacking two pickaxes).", NamedTextColor.GRAY).decorate(TextDecoration.ITALIC)));

        sender.sendMessage(Component.text("/blc giveItem <customName>", NamedTextColor.YELLOW)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text("Gives you a previously saved custom item.", NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/blc getMetaData", NamedTextColor.YELLOW)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text("Checks if your currently held item has a saved toolId.", NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/blc spawnMob <EntityType> <mobId> [world x y z]", NamedTextColor.YELLOW)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text("Spawns a custom mob. ", NamedTextColor.WHITE))
                .append(Component.text("Both world and coordinates are optional. If omitted entirely, it spawns at your location.", NamedTextColor.GRAY).decorate(TextDecoration.ITALIC)));

        sender.sendMessage(Component.text("=======================================", NamedTextColor.GOLD));

        return true;
    }
}