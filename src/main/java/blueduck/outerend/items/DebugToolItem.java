package blueduck.outerend.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DebugToolItem extends Item {
	public DebugToolItem(Properties properties) {
		super(properties.maxStackSize(1));
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
