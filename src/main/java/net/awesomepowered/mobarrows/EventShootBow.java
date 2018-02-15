package net.awesomepowered.mobarrows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class EventShootBow implements Listener {

    @EventHandler
    public void onShot(EntityShootBowEvent ev) {
        if (ev.getEntity() instanceof Player && ev.getProjectile() instanceof SpectralArrow) {
            Player p = (Player) ev.getEntity();
            for (int i = 0; i < 35; i++) {
                ItemStack item = p.getInventory().getItem(i);
                if (item != null && item.getType() == Material.MONSTER_EGG) {
                    SpawnEggMeta meta = (SpawnEggMeta) item.getItemMeta();
                    Entity ent = ev.getProjectile().getWorld().spawnEntity(ev.getProjectile().getLocation(), meta.getSpawnedType());
                    ent.setVelocity(ev.getProjectile().getVelocity());
                    ev.getProjectile().remove();
                    startTask(ent, p, ev.getForce(), MobArrows.instance);
                    return;
                }
            }
        }
    }

    public void startTask(Entity projectile, Player p, float damage, MobArrows plugin) {
        EntityArrow ea = new EntityArrow(projectile, p, damage, MobArrows.instance);
        ea.runTaskTimer(plugin,1,1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ea::cancel, plugin.getConfig().getLong("runTime", 5) * 20); //cancel the task if it's been running for 5 secs.
    }

}
