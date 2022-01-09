package tests;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	private static Main instance;

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;
		System.out.println("enabled");

		/*Minigame minigame = new Minigame.Builder(this)
				.addGoal(hitGoal)
				.addGoal(hitGoal)
				.addGoal(goldBlockGoal)
				.addGoal(standGoal)
				.addGoal(itemGoal)
				.addGoal(deathGoal)
				.build();

		 */
		/*Minigame minigame = new MinigameBuilder(this).setTeams(
				new TeamsConfiguration("4x2")
						.addActions(new TeamsActionBuilder(TeamsActionType.ALL)
							.addLocationChooser("spawn", ChooserType.GUI, PluginUtils.getItem(Material.GOLD_BLOCK, "§cgold"))
							.addLocationChooser("bett", ChooserType.COMMAND)
							.addLocationChooser("bett2", ChooserType.GUI).build())
						.addActions(new TeamsActionBuilder(TeamsActionType.ALL)
							.addLocationChooser("villager", ChooserType.GUI).build()))
						.setLocationChooserItem(new ItemStack(Material.COMPASS))
						.build();
		*/

		/*Minigame minigame = new MinigameBuilder(this).addSpawner(
				new ItemSpawner(Locations.getLocation("spawner").getBukkitLocation(),
						0, 200, new int[]{50}, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK))
				.addSpawner(
						new EntitySpawner(Locations.getLocation("spawner").getBukkitLocation(), 0, 200, new int[]{50}, EntityType.ZOMBIE))
				.setTeams(new TeamsConfiguration("1v2v3v4", TeamChooserType.AUTOMATIC)
						.addTeamsData(new TeamsDataBuilder()
								.addRespawnCondition(() -> Locations.getLocation("bett").isBlockPresent(Material.DIAMOND_BLOCK))
								.build(), TeamsConfiguration.ALL_TEAMS))
				.build();
				*/


		//new MinigameBuilder(this).addStartCondition(() -> Bukkit.getOnlinePlayers().size()>1).build();

		//Bukkit.getPluginManager().registerEvents(new FinalDeathListener(), this);
		//	Bukkit.getPluginManager().registerEvents(new InteractListener(), this);

		/*Minigame minigame = new MinigameBuilder(this).addEvent(new Event<>(PlayerDropItemEvent.class, e -> {
			System.out.println("drop: " + e.getItemDrop());
		})).build();*/

		/*
		Bedwars minigame TDD:
					MinigameBuilder							TeamsBuilder	 		TeamsActionBuilder
		Minigame = MinigameBuilder.newGame().setTeams(
											new TeamsConfiguration("4x4").forAllTeams()
											.addLocationChooser("spawn", ChooserType.GUI)
											.addLocationChooser("bett", ChooserType.GUI).build())
											.addItemSpawner(Material.GOLD, 20, new LocationChoser("GoldSpawner", ChooserType.GUI)
											.addGoal(GoalBuilder.newGoal(GoalType.BreakBlock(Material.bed))
		 */

	}

	public static Main getInstance() {
		return instance;
	}
}
