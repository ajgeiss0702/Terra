package com.dfsek.terra.bukkit.world.inventory.meta;

import com.dfsek.terra.api.inventory.ItemStack;
import com.dfsek.terra.api.inventory.item.Enchantment;
import com.dfsek.terra.bukkit.world.inventory.BukkitItemStack;


public class BukkitEnchantment implements Enchantment {
    private final org.bukkit.enchantments.Enchantment delegate;
    
    public BukkitEnchantment(org.bukkit.enchantments.Enchantment delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public org.bukkit.enchantments.Enchantment getHandle() {
        return delegate;
    }
    
    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return delegate.canEnchantItem(((BukkitItemStack) itemStack).getHandle());
    }
    
    @Override
    public boolean conflictsWith(Enchantment other) {
        return delegate.conflictsWith(((BukkitEnchantment) other).getHandle());
    }
    
    @Override
    public String getID() {
        return delegate.getKey().toString();
    }
    
    @Override
    public int getMaxLevel() {
        return delegate.getMaxLevel();
    }
}
