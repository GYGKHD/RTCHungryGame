package gygk.hungrygame.pojo;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GameItem {
    private String item;
    private String name;
    private int amount;
    private List<String> enchantment;
    private List<String> lore;

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public GameItem(String item, String name, int amount, List<String> enchantment, List<String> lore) {
        this.item = item;
        this.name = name;
        this.amount = amount;
        this.enchantment = enchantment;
        this.lore = lore;
    }

    public GameItem() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getEnchantment() {
        return enchantment;
    }

    public void setEnchantment(List<String> enchantment) {
        this.enchantment = enchantment;
    }
}
