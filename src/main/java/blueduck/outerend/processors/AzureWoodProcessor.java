package blueduck.outerend.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.NopProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.Template.BlockInfo;
import net.minecraft.world.gen.feature.template.Template.EntityInfo;
import net.minecraftforge.fml.ModList;

public class AzureWoodProcessor extends StructureProcessor
{
    // codec for loading the json
    public static final Codec<AzureWoodProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("modid").forGetter(AzureWoodProcessor::getModID),
            IStructureProcessorType.PROCESSOR_TYPE.optionalFieldOf("processor",  NopProcessor.INSTANCE).forGetter(AzureWoodProcessor::getSubProcessor)
    ).apply(instance, AzureWoodProcessor::new));

    // the IStructureProcessorType is just a codec supplier, this is used to register this processor type
    public static final IStructureProcessorType<AzureWoodProcessor> TYPE = () -> CODEC;

    private final String modID; public String getModID() { return this.modID; }
    private final StructureProcessor subProcessor; public StructureProcessor getSubProcessor() { return this.subProcessor; }

    public AzureWoodProcessor(String modID, StructureProcessor subProcessor)
    {
        this.modID = modID;
        this.subProcessor = ModList.get().isLoaded(modID)
                ? subProcessor
                : NopProcessor.INSTANCE; // we don't want to be doing a modloaded check on every block later
    }

    @Override
    protected IStructureProcessorType<?> getType()
    {
        return TYPE;
    }

    @Override
    public BlockInfo process(IWorldReader p_230386_1_, BlockPos p_230386_2_, BlockPos p_230386_3_, BlockInfo p_230386_4_, BlockInfo p_230386_5_, PlacementSettings p_230386_6_,
                             Template template)
    {
        return this.subProcessor.process(p_230386_1_, p_230386_2_, p_230386_3_, p_230386_4_, p_230386_5_, p_230386_6_, template);
    }

    @Override
    public EntityInfo processEntity(IWorldReader world, BlockPos seedPos, EntityInfo rawEntityInfo, EntityInfo entityInfo, PlacementSettings placementSettings, Template template)
    {
        return this.subProcessor.processEntity(world, seedPos, rawEntityInfo, entityInfo, placementSettings, template);
    }
}