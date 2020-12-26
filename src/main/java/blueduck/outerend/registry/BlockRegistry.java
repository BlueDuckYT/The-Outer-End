package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.blocks.EnderGrassBlock;
import blueduck.outerend.blocks.EnderSaplingBlock;
import blueduck.outerend.blocks.EnderTallGrass;
import blueduck.outerend.features.AzureTreeFeature;
import com.minecraftabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.minecraftabnormals.abnormals_core.common.items.AbnormalsSignItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegistry {
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OuterEndMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);

    public static final RegistryObject<Block> AZURE_STEM = BLOCKS.register("azure_stem", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Item> AZURE_STEM_ITEM = ITEMS.register("azure_stem", () -> new BlockItem(AZURE_STEM.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_PLANKS = BLOCKS.register("azure_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_PLANKS_ITEM = ITEMS.register("azure_planks", () -> new BlockItem(AZURE_PLANKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_SLAB = BLOCKS.register("azure_slab", () -> new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_SLAB_ITEM = ITEMS.register("azure_slab", () -> new BlockItem(AZURE_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_VERTICAL_SLAB = conditionallyRegisterBlock("azure_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)), () -> true);
    public static final RegistryObject<Item> AZURE_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("azure_vertical_slab", () -> new BlockItem(AZURE_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> true);

    public static final RegistryObject<Block> AZURE_STAIRS = BLOCKS.register("azure_stairs", () -> new StairsBlock(AZURE_PLANKS.get().getDefaultState(), Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_STAIRS_ITEM = ITEMS.register("azure_stairs", () -> new BlockItem(AZURE_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_DOOR = BLOCKS.register("azure_door", () -> new DoorBlock(Block.Properties.from(Blocks.OAK_DOOR).setOpaque((a,b,c)->a.get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER))));
    public static final RegistryObject<Item> AZURE_DOOR_ITEM = ITEMS.register("azure_door", () -> new BlockItem(AZURE_DOOR.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Block> AZURE_TRAPDOOR = BLOCKS.register("azure_trapdoor", () -> new TrapDoorBlock(Block.Properties.from(Blocks.OAK_TRAPDOOR).setOpaque((a,b,c)->false)));
    public static final RegistryObject<Item> AZURE_TRAPDOOR_ITEM = ITEMS.register("azure_trapdoor", () -> new BlockItem(AZURE_TRAPDOOR.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Block> AZURE_FENCE = BLOCKS.register("azure_fence", () -> new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_FENCE_ITEM = ITEMS.register("azure_fence", () -> new BlockItem(AZURE_FENCE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Block> AZURE_FENCE_GATE = BLOCKS.register("azure_fence_gate", () -> new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_FENCE_GATE_ITEM = ITEMS.register("azure_fence_gate", () -> new BlockItem(AZURE_FENCE_GATE.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Block> AZURE_BUTTON = BLOCKS.register("azure_button", () -> new WoodButtonBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_BUTTON_ITEM = ITEMS.register("azure_button", () -> new BlockItem(AZURE_BUTTON.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Block> AZURE_PRESSURE_PLATE = BLOCKS.register("azure_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_PRESSURE_PLATE_ITEM = ITEMS.register("azure_pressure_plate", () -> new BlockItem(AZURE_PRESSURE_PLATE.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Block> AZURE_SIGN = BLOCKS.register("azure_sign", () -> new AbnormalsStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), new ResourceLocation("outer_end:block/azure/sign")));
    public static final RegistryObject<Block> AZURE_WALL_SIGN = BLOCKS.register("azure_wall_sign", () -> new AbnormalsWallSignBlock(Block.Properties.from(Blocks.OAK_SIGN), new ResourceLocation("outer_end:block/azure/sign")));
    public static final RegistryObject<Item> AZURE_SIGN_ITEM = ITEMS.register("azure_sign", () -> new AbnormalsSignItem(AZURE_SIGN.get(), AZURE_WALL_SIGN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Block> AZURE_BOOKSHELF = conditionallyRegisterBlock("azure_bookshelf", () -> new BookshelfBlock(Block.Properties.from(Blocks.OAK_PLANKS)), () -> true);
    public static final RegistryObject<Item> AZURE_BOOKSHELF_ITEM = conditionallyRegisterItem("azure_bookshelf", () -> new BlockItem(AZURE_BOOKSHELF.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> true);



    public static final RegistryObject<Block> AZURE_LEAVES = BLOCKS.register("azure_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
    public static final RegistryObject<Item> AZURE_LEAVES_ITEM = ITEMS.register("azure_leaves", () -> new BlockItem(AZURE_LEAVES.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_STAMEN = BLOCKS.register("azure_stamen", () -> new Block(Block.Properties.from(Blocks.SHROOMLIGHT)));
    public static final RegistryObject<Item> AZURE_STAMEN_ITEM = ITEMS.register("azure_stamen", () -> new BlockItem(AZURE_STAMEN.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_SAPLING = BLOCKS.register("azure_bud", () -> new EnderSaplingBlock(Block.Properties.from(Blocks.OAK_SAPLING), AzureTreeFeature::generateTree));
    public static final RegistryObject<Item> AZURE_SAPLING_ITEM = ITEMS.register("azure_bud", () -> new BlockItem(AZURE_SAPLING.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    
    public static final RegistryObject<Block> ENDER_ROOTS = BLOCKS.register("ender_roots", () -> new EnderTallGrass(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryObject<Item> ENDER_ROOTS_ITEM = ITEMS.register("ender_roots", () -> new BlockItem(ENDER_ROOTS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Block> AZURE_GRASS = BLOCKS.register("azure_grass", () -> new EnderGrassBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE).setRequiresTool().hardnessAndResistance(3.0F, 9.0F), Blocks.END_STONE::getDefaultState, (rand)-> ENDER_ROOTS.get().getDefaultState()));
    public static final RegistryObject<Item> AZURE_GRASS_ITEM = ITEMS.register("azure_grass", () -> new BlockItem(AZURE_GRASS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    
    public static final RegistryObject<Block> HIMMEL_BLOCK = BLOCKS.register("himmel_block", () -> new Block(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_BLOCK_ITEM = ITEMS.register("himmel_block", () -> new BlockItem(HIMMEL_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_PILLAR = BLOCKS.register("himmel_pillar", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_PILLAR_ITEM = ITEMS.register("himmel_pillar", () -> new BlockItem(HIMMEL_PILLAR.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_SLAB = BLOCKS.register("himmel_slab", () -> new SlabBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_SLAB_ITEM = ITEMS.register("himmel_slab", () -> new BlockItem(HIMMEL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_VERTICAL_SLAB = conditionallyRegisterBlock("himmel_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)), () -> true);
    public static final RegistryObject<Item> HIMMEL_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("himmel_vertical_slab", () -> new BlockItem(HIMMEL_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> true);

    public static final RegistryObject<Block> HIMMEL_STAIRS = BLOCKS.register("himmel_stairs", () -> new StairsBlock(HIMMEL_BLOCK.get().getDefaultState(), Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_STAIRS_ITEM = ITEMS.register("himmel_stairs", () -> new BlockItem(HIMMEL_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    
    
    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<Item> conditionallyRegisterItem(String registryName, Supplier<Item> item, Supplier<Boolean> condition) {
        if (condition.get())
            return ITEMS.register(registryName, item);
        return null;
    }
    public static RegistryObject<Block> conditionallyRegisterBlock(String registryName, Supplier<Block> block, Supplier<Boolean> condition) {
        if (condition.get())
            return BLOCKS.register(registryName, block);
        return null;
    }


}
