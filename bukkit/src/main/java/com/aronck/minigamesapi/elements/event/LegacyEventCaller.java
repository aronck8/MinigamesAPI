package com.aronck.minigamesapi.elements.event;

import com.aronck.minigamesapi.events.custom.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

@SuppressWarnings("deprecated")
public class LegacyEventCaller implements Listener {

    private EventsManager eventsManager;

    LegacyEventCaller(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerLeashEntityEvent(PlayerLeashEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockCanBuildEvent(BlockCanBuildEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockDamageEvent(BlockDamageEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockExpEvent(BlockExpEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onFurnaceExtractEvent(FurnaceExtractEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBrewEvent(BrewEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onFurnaceBurnEvent(FurnaceBurnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onFurnaceSmeltEvent(FurnaceSmeltEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onNotePlayEvent(NotePlayEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onSignChangeEvent(SignChangeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onCreeperPowerEvent(CreeperPowerEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityCombustEvent(EntityCombustEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityCreatePortalEvent(EntityCreatePortalEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityInteractEvent(EntityInteractEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityPortalEnterEvent(EntityPortalEnterEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityTameEvent(EntityTameEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityTargetEvent(EntityTargetEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityTeleportEvent(EntityTeleportEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityUnleashEvent(EntityUnleashEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onHorseJumpEvent(HorseJumpEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onItemDespawnEvent(ItemDespawnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPigZapEvent(PigZapEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onSheepDyeWoolEvent(SheepDyeWoolEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onSlimeSplitEvent(SlimeSplitEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityBreakDoorEvent(EntityBreakDoorEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityPortalEvent(EntityPortalEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEntityPortalExitEvent(EntityPortalExitEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerUnleashEntityEvent(PlayerUnleashEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onExpBottleEvent(ExpBottleEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onHangingBreakEvent(HangingBreakEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onHangingPlaceEvent(HangingPlaceEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onEnchantItemEvent(EnchantItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryCreativeEvent(InventoryCreativeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerAchievementAwardedEvent(PlayerAchievementAwardedEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerAnimationEvent(PlayerAnimationEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerChannelEvent(PlayerChannelEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerEditBookEvent(PlayerEditBookEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerItemBreakEvent(PlayerItemBreakEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onPlayerVelocityEvent(PlayerVelocityEvent e){
        callEvents(e);
    }


    //######################
    //#    Custom Events   #
    //######################
    @EventHandler
    public void onFinalDeathEvent(FinalDeathEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onFirstSpawnEvent(FirstSpawnEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onGameOverEvent(GameOverEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onMinigameStartEvent(MinigameStartEvent e){
        callEvents(e);
    }

    @EventHandler
    public void onShopBuyEvent(ShopBuyEvent<?, ?> e){
        callEvents(e);
    }

    @EventHandler
    public void onTeamEliminateEvent(TeamEliminateEvent e){
        callEvents(e);
    }

    public void callEvents(Event event){
        if(eventsManager.debug)System.out.println("Event triggered: " + event.getEventName());
        if(eventsManager.getEvents().get(event.getClass())==null)return;
        for(IEvent iEvent : eventsManager.getEvents().get(event.getClass())){
            iEvent.run(event);
        }
    }

}
