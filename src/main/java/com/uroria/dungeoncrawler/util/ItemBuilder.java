package com.uroria.dungeoncrawler.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.*;

public class ItemBuilder {

    /**
     *  The ItemBuilder offer many function to create & modify an ItemStack
     */

    private final ItemStack stack;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }

    /**
     * set the displayName of an ItemStack
     * */


    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    /**
     * decides if an ItemStack is unbreakable
     */

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level, boolean restriction) {
        meta.addEnchant(enchantment, level, restriction);
        return this;
    }

    public ItemBuilder setRandomEnchantArmor() {
        List<Enchantment> list = new ArrayList<>();
        list.add(Enchantment.PROTECTION_PROJECTILE); list.add(Enchantment.PROTECTION_FIRE); list.add(Enchantment.THORNS);
        meta.addEnchant(list.get(new Random().nextInt(list.size())), 1, true);
        return this;
    }

    public ItemBuilder setRandomEnchantWeapon() {
        List<Enchantment> list = new ArrayList<>();
        list.add(Enchantment.DURABILITY); list.add(Enchantment.DAMAGE_ALL); list.add(Enchantment.KNOCKBACK);
        meta.addEnchant(list.get(new Random().nextInt(list.size())), 1, true);
        return this;
    }

    /**
     * set the durability of an ItemStack
     */

    public ItemBuilder setDurability(int amount) {
        stack.setDurability((short)amount);
        return this;
    }

    /**
     * creates a list which will be added to the lore
     */

    public ItemBuilder setLore(String... name) {
        List<String> loreList = new ArrayList<>();
        Collections.addAll(loreList, name);
        meta.setLore(loreList);
        return this;
    }


    /**
     * adds a SkullMeta to the ItemStack
     */

    public ItemBuilder setSkull(String skullOwner) {
        if(stack.getType() != Material.PLAYER_HEAD)
            return this;
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwner(skullOwner);
        stack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(color);
        stack.setItemMeta(armorMeta);
        return this;
    }

    /**
     * make an ItemStack glow
     */

    public ItemBuilder setGlow(boolean b) {
        stack.addUnsafeEnchantment(Enchantment.LURE, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * modifies the ItemFlag
     */

    public ItemBuilder modifyItemFlag(ItemFlag itemFlag) {
        stack.addItemFlags(itemFlag);
        return this;
    }

    /**
     * remove an ItemFlag
     */

    public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        stack.removeItemFlags(itemFlag);
        return this;
    }

    /**
     * modifies the ItemStack attribute
     */

    public ItemBuilder setAttribute(Attribute attribute, String name, double d, EquipmentSlot equipmentSlot) {
        Objects.requireNonNull(meta.getAttributeModifiers(attribute)).add(
                new AttributeModifier(UUID.randomUUID(),name, d, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot)
        );
        return this;
    }

    /**
     * remove the attribute
     */

    public ItemBuilder removeAttribute(Attribute attribute) {
        meta.removeAttributeModifier(attribute);
        return this;
    }

    /**
     * sets the damage to the ItemStack
     */

    public ItemBuilder setDamage(double attackDamage) {
        Objects.requireNonNull(meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE)).add(
                new AttributeModifier(UUID.randomUUID(),"attackDamage", attackDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
        );
        return this;
    }

    /**
     * sets the attackSpeed to the ItemStack
     */

    public ItemBuilder setAttackSpeed(double attackSpeed) {
        Objects.requireNonNull(meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED)).add(
                new AttributeModifier(UUID.randomUUID(),"attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
        );
        return this;
    }

    public ItemBuilder setFirework(int power, Color color, FireworkEffect.Type type) {
        FireworkMeta fireworkMeta = (FireworkMeta) stack.getItemMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withColor(color);
        builder.with(type);
        fireworkMeta.addEffect(builder.build());
        fireworkMeta.setPower(power);
        stack.setItemMeta(fireworkMeta);
        return this;
    }

    public ItemBuilder setTrippedArrow(PotionType type) {
        PotionMeta potionMeta = (PotionMeta) meta;
        potionMeta.setBasePotionData(new PotionData(type));
        stack.setItemMeta(potionMeta);
        return this;
    }

    /**
     * returns an ItemStack
     */

    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
