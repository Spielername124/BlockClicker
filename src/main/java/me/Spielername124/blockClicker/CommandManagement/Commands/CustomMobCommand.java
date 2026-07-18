package me.Spielername124.blockClicker.CommandManagement.Commands;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class CustomMobCommand {
    public static boolean spawnMob(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("invalid command usage");
        }
        //get the type and the mobId
        EntityType type;
        try {
            type = EntityType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(args[1] + " is not a valid entity type");
            return true;
        }

        String customMobId = args[2];

        //get the spawn location
        Location location = getLocation(sender, args);
        //if there is no valid location, return
        if (location == null) return true;

        // spawn the entity
        Entity spawnedEntity = location.getWorld().spawnEntity(location, type);

        // ad the custom id to the mob
        if (customMobId != null && !customMobId.isEmpty()) {
            spawnedEntity.getPersistentDataContainer().set(
                    BlockClicker.MOB_ID_KEY,
                    PersistentDataType.STRING,
                    customMobId
            );
        }
        return true;
    }

    private static Location getLocation(CommandSender sender, String[] args) {
        //if only the penitentiary and the id are stated, return the player location if sent by a player and else null
        if (args.length == 3){
            Location location = null;
            if(sender instanceof Player)
                location = ((Player) sender).getLocation();
            if (location == null)
                sender.sendMessage("you have to state a valid world + coordinates");
            return location;
        }

        // get a world in which the mob should be spawned
        World world = null;
        int offset = 0;

        //read the world if given as argument
        if (args.length == 7) {
            world = Bukkit.getWorld(args[3]);
            offset++;
            if (world == null) {
                sender.sendMessage("invalid world");
                return null;
            }
        }
        //if it is not given as an argument we try to get it from the sending player, if the sender is a player, and els admit defeat
        else if (!(sender instanceof Player player)) {
            sender.sendMessage("only player can omit the world argument");
            return null;
        }
        else {
            world = player.getWorld();
        }

        // we try to get the coordinates from the command
        double x, y, z;
        try {
            x = Double.parseDouble(args[3+offset]);
            y = Double.parseDouble(args[4+offset]);
            z = Double.parseDouble(args[5+offset]);
        } catch (Exception e) {
            sender.sendMessage("The coordinates are not valid");
            return null;
        }
        return new Location(world, x, y, z);
    }
}


