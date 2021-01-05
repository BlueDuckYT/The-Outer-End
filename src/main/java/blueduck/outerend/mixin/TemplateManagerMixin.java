package blueduck.outerend.mixin;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemplateManager.class)
public class TemplateManagerMixin {
    public ResourceLocation loadingStructure;

    //this allows us to get away with adding something to structures without replacing the nbt file for the structure and without datapacks
    //TODO: find a way to make it so that this can be canceled or prevented
    @Inject(at = @At("HEAD"), method = "func_227458_a_(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/world/gen/feature/template/Template;")
    public void OUTER_END_loadTemplate(CompoundNBT p_227458_1_, CallbackInfoReturnable<Template> cir) {
        if (loadingStructure.equals(new ResourceLocation("minecraft:end_city/base_floor"))) {
            ListNBT blocks = p_227458_1_.getList("blocks", 10);
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("state", 6);
            nbt.putIntArray("pos", new int[]{0, -1, 0});
            CompoundNBT nbtData = new CompoundNBT();
            nbtData.putString("metadata", "outer_end:purpur_golem");
            nbtData.putString("mirror", "NONE");
            nbtData.putInt("ignoreEntities", 0);
            nbtData.putInt("powered", 0);
            nbtData.putInt("seed", 0);
            nbtData.putString("author", "GiantLuigi4");
            nbtData.putString("rotation", "NONE");
            nbtData.putString("mode", "DATA");
            nbtData.putInt("posX", 0);
            nbtData.putInt("posY", 0);
            nbtData.putInt("posZ", 0);
            nbtData.putInt("sizeX", 0);
            nbtData.putInt("sizeY", 0);
            nbtData.putInt("sizeZ", 0);
            nbtData.putInt("integrity", 0);
            nbtData.putInt("showair", 0);
            nbtData.putString("id", "minecraft:structure_block");
            nbtData.putInt("showboundingbox", 0);
            nbt.put("nbt", nbtData);
            blocks.add(nbt);
        }
    }

    @Inject(at = @At("HEAD"), method = "loadTemplateResource(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/world/gen/feature/template/Template;")
    public void OUTER_END_preLoad(ResourceLocation p_209201_1_, CallbackInfoReturnable<Template> cir) {
        loadingStructure = p_209201_1_;
    }

    @Inject(at = @At("TAIL"), method = "loadTemplateResource(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/world/gen/feature/template/Template;")
    public void OUTER_END_postLoad(ResourceLocation p_209201_1_, CallbackInfoReturnable<Template> cir) {
        loadingStructure = null;
    }
}
