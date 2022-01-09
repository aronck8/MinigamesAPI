package tests.shops;

import com.aronck.minigamesapi.elements.shop.CategorizedShop;
import com.aronck.minigamesapi.elements.shop.ShopCategory;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tests.utils.PluginConstants;

import java.util.ArrayList;

public class SmallShop extends CategorizedShop<ItemStack, ItemStack> {


    public SmallShop() {
        super("name", 1);
        initShop();
    }

    private void initShop(){

        //init blocks category
        ShopCategory<ItemStack, ItemStack> blocks = new ShopCategory<>(this,"§aBlocks", PluginUtils.getItem(Material.SANDSTONE, "§aBlocks"));
        //blocks.addProduct(PluginUtils.getItem(Material.SANDSTONE, 16, "§aSandstein"), PluginConstants.BRONZE);
        //blocks.addProduct(new ItemStack(Material.STONE, 4), PluginConstants.BRONZE);

        //ShopCategory<ItemStack, ItemStack> swords = new ShopCategory<>(this, "§bSchwerter", PluginUtils.getItem(Material.GOLDEN_SWORD, "§cSchwerter"));
        //swords.addProduct(PluginUtils.getItem(Material.DIAMOND_SWORD, "§cdia ding"), new ItemStack(Material.DIAMOND, 4));
    }

    @Override
    public boolean buy(ItemStack product, ItemStack prize, Player player) {
        if(!player.getInventory().containsAtLeast(prize, 1)){
            player.sendMessage("§cDu hast nicht genügend Materialien dazu!");
            return false;
        }
        player.getInventory().removeItem(prize);
        player.getInventory().addItem(product);
        player.updateInventory();

        return true;
    }

    @Override
    public ItemStack getItemStack(ItemStack key, ItemStack value) {
        ItemStack item = key.clone();
        ArrayList<String> lores = new ArrayList<>();

        lores.add("preis: " + value.getAmount() + " " + value.getType().toString());
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

}
