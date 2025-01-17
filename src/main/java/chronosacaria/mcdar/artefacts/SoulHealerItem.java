package chronosacaria.mcdar.artefacts;

import chronosacaria.mcdar.api.AOEHelper;
import chronosacaria.mcdar.Mcdar;
import chronosacaria.mcdar.config.McdarConfig;
import chronosacaria.mcdar.enchants.EnchantID;
import chronosacaria.mcdar.enums.DefenciveArtefactID;
import chronosacaria.mcdar.init.EnchantsRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SoulHealerItem extends ArtefactDefenciveItem{
    public SoulHealerItem(DefenciveArtefactID artefactID) {
        super(artefactID);
    }

    public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand){
        ItemStack itemStack = user.getStackInHand(hand);

        if (user.totalExperience >= 20 || user.isCreative()){
            if ((user.getHealth() < user.getMaxHealth())){
                float currentHealth = user.getHealth();
                float maxHealth = user.getMaxHealth();
                float lostHealth = maxHealth - currentHealth;
                float healedAmount;
                if (lostHealth < (maxHealth * 0.20F)){
                    user.setHealth(currentHealth + lostHealth);
                    healedAmount = lostHealth;
                } else {
                    user.setHealth(currentHealth + (maxHealth * 0.20F));
                    healedAmount = (maxHealth * 0.20F);
                }
                if (!user.isCreative()){
                    user.addExperience((int)(-healedAmount));
                    itemStack.damage(1, user, (entity) -> entity.sendToolBreakStatus(hand));
                }
                user.getItemCooldownManager().set(this, 20);
            } else {
                float healedAmount = AOEHelper.healMostInjuredAlly(user, 12);
                if (healedAmount > 0){
                    if (!user.isCreative()){
                        user.addExperience((int)(-healedAmount));
                        itemStack.damage(1, user, (entity) -> entity.sendToolBreakStatus(hand));
                    }
                    int cooldownLevel = EnchantmentHelper.getEquipmentLevel(EnchantsRegistry.enchants.get(EnchantID.COOLDOWN),
                            user);
                    if (cooldownLevel > 0) {
                        user.getItemCooldownManager().set(this, (int) ((cooldownLevel * 0.1) * 60));
                    } else {
                        user.getItemCooldownManager().set(this, 60);
                    }
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.soul_healer_1").formatted(Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.soul_healer_2").formatted(Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.soul_healer_3").formatted(Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.soul_healer_4").formatted(Formatting.ITALIC));
    }
}
