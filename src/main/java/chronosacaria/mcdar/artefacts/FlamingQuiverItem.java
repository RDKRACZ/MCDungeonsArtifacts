package chronosacaria.mcdar.artefacts;

import chronosacaria.mcdar.enchants.EnchantID;
import chronosacaria.mcdar.enums.QuiverArtefactID;
import chronosacaria.mcdar.init.EnchantsRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class FlamingQuiverItem extends ArtefactQuiverItem{
    public FlamingQuiverItem(QuiverArtefactID artefactID) {
        super(artefactID);
    }

    public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand){
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.isCreative()){
            itemStack.damage(1, user, (entity) -> entity.sendToolBreakStatus(hand));
        }
        int cooldownLevel = EnchantmentHelper.getEquipmentLevel(EnchantsRegistry.enchants.get(EnchantID.COOLDOWN),
                user);
        if (cooldownLevel > 0) {
            user.getItemCooldownManager().set(this, cooldownLevel * 500);
        } else {
            user.getItemCooldownManager().set(this, 500);
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.flaming_quiver_1").formatted(Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.flaming_quiver_2").formatted(Formatting.ITALIC));
    }
}
