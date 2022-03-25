package org.glockinmybape.tattylobby;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Logger;

public class TattyLobby extends JavaPlugin implements Listener {
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        if (this.getConfig().getBoolean("Time.fixed")) {
            for (World w : Bukkit.getServer().getWorlds()) {
                w.setTime(this.getConfig().getLong("Time.meaning"));
                w.setGameRuleValue("doDaylightCycle", "false");
            }
            Logger log = Bukkit.getLogger();
            log.info("§b");
            log.info("§b .----------------------------------------------------------. ");
            log.info("§b| .-------------------------------------------------------. |");
            log.info("§b| |             \t\t\t\t\t\t");
            log.info("§b| |            §7Плагин: §bTattyLobby§8| §7Версия: §b1.0                ");
            log.info("§b| |        §7Создан для §bTattyWorld §8- §7Разработал: §bglockinmybape\t");
            log.info("§b| |                    §bvk.com/TattyWorld");
            log.info("§b| |             \t\t\t\t\t\t");
            log.info("§b| '-------------------------------------------------------'§b|");
            log.info("§b'-----------------------------------------------------------'");
            log.info("§b");
        }

        this.getLogger().info("Plugin successfully initialized.");
    }

    public void onDisable() {
        this.getLogger().info("Plugin successfully disabled.");
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent e) {
        if (this.getConfig().getBoolean("Settings.noHunger")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getActivePotionEffects().clear();
        p.setLevel(0);
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setJoinMessage("");
        }

        if (this.getConfig().getBoolean("Effects.Invisibility")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
        }

        if (this.getConfig().getBoolean("Effects.Speed")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
        }

        if (this.getConfig().getBoolean("Effects.BlindNess")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
        }

        if (this.getConfig().getBoolean("LvlonJoin.enabled")) {
            p.setLevel(this.getConfig().getInt("LvlonJoin.amount"));
        }

        String gm = this.getConfig().getString("Settings.GameMode");
        if (gm != null) {
            p.setGameMode(GameMode.valueOf(gm));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setQuitMessage("");
        }

    }

    @EventHandler
    public void noDeath(PlayerDeathEvent e) {
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setDeathMessage("");
        }

    }

    @EventHandler
    public void noDamage(EntityDamageEvent e) {
        if (this.getConfig().getBoolean("Settings.noDamage")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassBreak")) && this.getConfig().getBoolean("Settings.noBreak")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassPlace")) && this.getConfig().getBoolean("Settings.noPlace")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noFire(BlockBurnEvent e) {
        if (this.getConfig().getBoolean("Settings.noFire")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noFire(BlockSpreadEvent e) {
        if (this.getConfig().getBoolean("Settings.noFire")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noChat(AsyncPlayerChatEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassChat")) && this.getConfig().getBoolean("Settings.noChat")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void oCommands(PlayerCommandPreprocessEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassCommands"))) {
            if (this.getConfig().getBoolean("Settings.noCommands") && !this.getConfig().getStringList("allowed_commands").contains(e.getMessage().split(" ")[0].replace("/", ""))) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (this.getConfig().getBoolean("Settings.noExplosions")) {
            e.setCancelled(true);
        }

    }
}
