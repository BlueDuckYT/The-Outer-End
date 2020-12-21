package blueduck.outerend.client.colors.items;

import blueduck.outerend.items.OuterEndSpawnEgg;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class SpawnEggColors implements IItemColor {
	@Override
	public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
		if (p_getColor_2_%2 == 0) {
			return ((OuterEndSpawnEgg)p_getColor_1_.getItem()).primColor;
		} else {
			return ((OuterEndSpawnEgg)p_getColor_1_.getItem()).secColor;
		}
	}
}
