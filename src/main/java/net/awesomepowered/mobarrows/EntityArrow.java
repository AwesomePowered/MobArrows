package net.awesomepowered.mobarrows;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityArrow extends BukkitRunnable {

    private Entity proj;
    private Player player;
    private float damage;
    private MobArrows plugin;

    public EntityArrow(Entity projectile, Player p, float damage, MobArrows plugin) {
        this.proj = projectile;
        this.player = p;
        this.damage = damage;
        this.plugin = plugin;
    }


    @Override
    public void run() {
        if ((proj.isOnGround()) || (proj.isDead()) || proj.getLocation().getBlockY() > 300) {
            cancel();
            proj.remove();
            return;
        }

        for (Entity e : proj.getLocation().getChunk().getEntities()) {
            if (e.getLocation().distance(proj.getLocation()) < plugin.getConfig().getDouble("hitbox", 2) && e instanceof LivingEntity && !(e == player) && !(e == proj)) {
                ((LivingEntity)e).damage(plugin.getConfig().getDouble("baseDamage", 5)*damage, player);
                cancel();
                proj.remove();
                return;
            }
        }
        proj.getWorld().playSound(proj.getLocation(), Sound.valueOf(plugin.getConfig().getString("types."+proj.getType().toString()+".sound", "ENTITY_GHAST_HURT")), 1, 1);
    }
}