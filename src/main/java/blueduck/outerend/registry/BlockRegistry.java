package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.blocks.DyingBlock;
import blueduck.outerend.blocks.EnderSaplingBlock;
import blueduck.outerend.features.AzureTreeFeature;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OuterEndMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);

    public static final RegistryObject<Block> AZURE_STEM = BLOCKS.register("azure_stem", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Item> AZURE_STEM_ITEM = ITEMS.register("azure_stem", () -> new BlockItem(AZURE_STEM.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_PLANKS = BLOCKS.register("azure_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_PLANKS_ITEM = ITEMS.register("azure_planks", () -> new BlockItem(AZURE_PLANKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_LEAVES = BLOCKS.register("azure_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
    public static final RegistryObject<Item> AZURE_LEAVES_ITEM = ITEMS.register("azure_leaves", () -> new BlockItem(AZURE_LEAVES.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_STAMEN = BLOCKS.register("azure_stamen", () -> new Block(Block.Properties.from(Blocks.SHROOMLIGHT)));
    public static final RegistryObject<Item> AZURE_STAMEN_ITEM = ITEMS.register("azure_stamen", () -> new BlockItem(AZURE_STAMEN.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_BLOCK = BLOCKS.register("himmel_block", () -> new Block(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_BLOCK_ITEM = ITEMS.register("himmel_block", () -> new BlockItem(HIMMEL_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_PILLAR = BLOCKS.register("himmel_pillar", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_PILLAR_ITEM = ITEMS.register("himmel_pillar", () -> new BlockItem(HIMMEL_PILLAR.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_SLAB = BLOCKS.register("himmel_slab", () -> new SlabBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_SLAB_ITEM = ITEMS.register("himmel_slab", () -> new BlockItem(HIMMEL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> HIMMEL_STAIRS = BLOCKS.register("himmel_stairs", () -> new StairsBlock(HIMMEL_BLOCK.get().getDefaultState(), Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_STAIRS_ITEM = ITEMS.register("himmel_stairs", () -> new BlockItem(HIMMEL_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_SAPLING = BLOCKS.register("azure_bud", () -> new EnderSaplingBlock(Block.Properties.from(Blocks.OAK_SAPLING), AzureTreeFeature::generateTree));
    public static final RegistryObject<Item> AZURE_SAPLING_ITEM = ITEMS.register("azure_bud", () -> new BlockItem(AZURE_SAPLING.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Block> AZURE_GRASS = BLOCKS.register("azure_grass", () -> new DyingBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE).setRequiresTool().hardnessAndResistance(3.0F, 9.0F), Blocks.END_STONE::getDefaultState));
    public static final RegistryObject<Item> AZURE_GRASS_ITEM = ITEMS.register("azure_grass", () -> new BlockItem(AZURE_GRASS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
