package me.rananaah.CustomTreasureMap;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTreasureMap extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("CustomTreasureMap plugin enabled!");
        getCommand("treasuremap").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                giveTreasureMap((Player) sender);
                sender.sendMessage("\u00A76You have received a treasure map!");
            } else {
                sender.sendMessage("Only players can use this command!");
            }
            return true;
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomTreasureMap plugin disabled!");
    }

    private void giveTreasureMap(Player player) {
        World world = player.getWorld();
        Random random = new Random();

        // Generate random treasure location
        int x = random.nextInt(1000) - 500; // -500 to 500 range
        int z = random.nextInt(1000) - 500;
        int y = world.getHighestBlockYAt(x, z);
        
        Location treasureLocation = new Location(world, x, y, z);

        // Create a map item
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) map.getItemMeta();

        // Create a map view centered at treasure location
        MapView mapView = Bukkit.createMap(world);
        mapView.setCenterX(x);
        mapView.setCenterZ(z);
        mapView.setScale(MapView.Scale.NORMAL);
        
        mapMeta.setMapView(mapView);
        mapMeta.setDisplayName("\u00A76Treasure Map");
        map.setItemMeta(mapMeta);

        // Give the map to the player
        player.getInventory().addItem(map);
        player.sendMessage("\u00A76Your treasure is located near X: " + x + " Z: " + z);
    }
}
