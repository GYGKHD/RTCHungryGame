package gygk.hungrygame.event;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class EntityDamageBy implements Listener {
    @EventHandler
    public void EntityDamageByOther(ProjectileHitEvent event){
        if(event.getEntity().getType() == EntityType.SNOWBALL){
            Entity hitEntity = event.getHitEntity();
            if(!Objects.isNull(hitEntity) && hitEntity.getType() == EntityType.PLAYER){
                Player hitPlayer = (Player) hitEntity;

                hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,2));

            }
        }
        if(event.getEntity().getType() == EntityType.EGG){
            Block hitBlock = event.getHitBlock();
            Entity hitEntity = event.getHitEntity();
            if(!Objects.isNull(hitEntity) && hitEntity.getType() == EntityType.PLAYER){
                Player hitPlayer = (Player) hitEntity;
                hitPlayer.getWorld().createExplosion(hitPlayer.getLocation(),1,false,false);
            }
            if (!Objects.isNull(hitBlock)){
                hitBlock.getWorld().createExplosion(hitBlock.getLocation(),1,false,false);
            }
        }
    }

    @EventHandler
    public void EggBoom(PlayerEggThrowEvent event){
        event.setHatching(false);
    }


}
