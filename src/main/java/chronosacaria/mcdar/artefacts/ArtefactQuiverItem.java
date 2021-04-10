package chronosacaria.mcdar.artefacts;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class ArtefactQuiverItem extends Item {
    public ArtefactQuiverItem(Settings settings) {
        super(settings.maxCount(1).maxDamage(7));
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }
}
