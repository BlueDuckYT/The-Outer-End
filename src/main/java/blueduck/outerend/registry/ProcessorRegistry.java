package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.processors.IfLoadedProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class ProcessorRegistry {

	public static IStructureProcessorType<IfLoadedProcessor> IF_LOADED_PROCESSOR = () -> IfLoadedProcessor.CODEC;

	public static void registerProcessors() {
		Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(OuterEndMod.MODID, "if_loaded"), IF_LOADED_PROCESSOR);
	}
}
