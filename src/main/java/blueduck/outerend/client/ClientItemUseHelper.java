package blueduck.outerend.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.EntityRayTraceResult;

public class ClientItemUseHelper {
	public static boolean isLookingAtEntity() {
		return Minecraft.getInstance().objectMouseOver instanceof EntityRayTraceResult;
}
}
