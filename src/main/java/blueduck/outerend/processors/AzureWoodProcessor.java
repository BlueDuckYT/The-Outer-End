package blueduck.outerend.processors;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;

public class AzureWoodProcessor extends StructureProcessor {
    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.RULE;
    }

    @Nullable
    public Template.BlockInfo process(IWorldReader p_230386_1_, BlockPos p_230386_2_, BlockPos p_230386_3_, Template.BlockInfo p_230386_4_, Template.BlockInfo p_230386_5_, PlacementSettings p_230386_6_, @Nullable Template template) {
        return func_230386_a_(p_230386_1_, p_230386_2_, p_230386_3_, p_230386_4_, p_230386_5_, p_230386_6_);
    }
}
