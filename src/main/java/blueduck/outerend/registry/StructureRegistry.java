package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.structures.CatacombsStructure;
import blueduck.outerend.structures.EndTowerStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class StructureRegistry {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, OuterEndMod.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> END_TOWER = setupStructure("end_tower", () -> (new EndTowerStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> CATACOMBS = setupStructure("catacombs", () -> (new CatacombsStructure(NoFeatureConfig.field_236558_a_)));

    private static <T extends Structure<?>> RegistryObject<T> setupStructure(String name, Supplier<T> structure) {
        return STRUCTURES.register(name, structure);
    }

    public static void init() {
        STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void setupStructures() {
        setupStructure(
                END_TOWER.get(), /* The instance of the structure */
                new StructureSeparationSettings(OuterEndMod.CONFIG.END_TOWER_MAX.get() /* maximum distance apart in chunks between spawn attempts */,
                        OuterEndMod.CONFIG.END_TOWER_MIN.get() /* minimum distance apart in chunks between spawn attempts */,
                        753937990 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                false);

        setupStructure(
                CATACOMBS.get(), /* The instance of the structure */
                new StructureSeparationSettings(OuterEndMod.CONFIG.CATACOMBS_MAX.get() /* maximum distance apart in chunks between spawn attempts */,
                        OuterEndMod.CONFIG.CATACOMBS_MIN.get() /* minimum distance apart in chunks between spawn attempts */,
                        125261290 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                false);

    }

    public static <F extends Structure<?>> void setupStructure(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        /*
         * We need to add our structures into the map in Structure alongside vanilla
         * structures or else it will cause errors. Called by registerStructure.
         *
         * If the registration is setup properly for the structure,
         * getRegistryName() should never return null.
         */
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        /*
         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
         *
         * Note: The air space this method will create will be filled with water if the structure is below sealevel.
         * This means this is best for structure above sealevel so keep that in mind.
         */
        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        /*
         * Adds the structure's spacing into several places so that the structure's spacing remains
         * correct in any dimension or worldtype instead of not spawning.
         *
         * However, it seems it doesn't always work for code made dimensions as they read from
         * this list beforehand. Use the WorldEvent.Load event in StructureTutorialMain to add
         * the structure spacing from this list into that dimension.
         */
        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}
