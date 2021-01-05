package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.blocks.CrystalBlock;
import blueduck.outerend.blocks.EnderGrassBlock;
import blueduck.outerend.blocks.EnderSaplingBlock;
import blueduck.outerend.blocks.EnderTallGrass;
import blueduck.outerend.features.AzureTreeFeature;
import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsBeehiveBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.chest.AbnormalsChestBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.chest.AbnormalsTrappedChestBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.AbnormalsLogBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.StrippedLogBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.StrippedWoodBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodBlock;
import com.minecraftabnormals.abnormals_core.common.items.AbnormalsSignItem;
import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegistry {

    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OuterEndMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);

    public static BlockSubRegistryHelper HELPER = new BlockSubRegistryHelper(OuterEndMod.HELPER, ITEMS, BLOCKS);

    public static final RegistryObject<Block> AZURE_STRIPPED_STEM = BLOCKS.register("azure_stripped_stem", () -> new StrippedLogBlock(Block.Properties.from(Blocks.OAK_LOG)));

    public static final RegistryObject<Block> AZURE_STEM = BLOCKS.register("azure_stem", () -> new AbnormalsLogBlock(() -> AZURE_STRIPPED_STEM.get(), Block.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Item> AZURE_STEM_ITEM = ITEMS.register("azure_stem", () -> new BlockItem(AZURE_STEM.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistryObject<Item> AZURE_STRIPPED_STEM_ITEM = ITEMS.register("azure_stripped_stem", () -> new BlockItem(AZURE_STRIPPED_STEM.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_STRIPPED_PITH = BLOCKS.register("azure_stripped_pith", () -> new StrippedWoodBlock(Block.Properties.from(Blocks.OAK_LOG)));

    public static final RegistryObject<Block> AZURE_PITH = BLOCKS.register("azure_pith", () -> new WoodBlock(() -> AZURE_STRIPPED_PITH.get(), Block.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Item> AZURE_PITH_ITEM = ITEMS.register("azure_pith", () -> new BlockItem(AZURE_PITH.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistryObject<Item> AZURE_STRIPPED_PITH_ITEM = ITEMS.register("azure_stripped_pith", () -> new BlockItem(AZURE_STRIPPED_PITH.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));


    public static final RegistryObject<Block> AZURE_PLANKS = BLOCKS.register("azure_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_PLANKS_ITEM = ITEMS.register("azure_planks", () -> new BlockItem(AZURE_PLANKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_VERTICAL_PLANKS = conditionallyRegisterBlock("azure_vertical_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> AZURE_VERTICAL_PLANKS_ITEM = conditionallyRegisterItem("azure_vertical_planks", () -> new BlockItem(AZURE_VERTICAL_PLANKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> AZURE_SLAB = BLOCKS.register("azure_slab", () -> new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Item> AZURE_SLAB_ITEM = ITEMS.register("azure_slab", () -> new BlockItem(AZURE_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> AZURE_VERTICAL_SLAB = conditionallyRegisterBlock("azure_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> AZURE_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("azure_vertical_slab", () -> new BlockItem(AZURE_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

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

    public static final RegistryObject<Block> AZURE_SIGN = BLOCKS.register("azure_sign", () -> new AbnormalsStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), new ResourceLocation("outer_end:textures/block/azure_sign.png")));
    public static final RegistryObject<Block> AZURE_WALL_SIGN = BLOCKS.register("azure_wall_sign", () -> new AbnormalsWallSignBlock(Block.Properties.from(Blocks.OAK_SIGN), new ResourceLocation("outer_end:textures/block/azure_sign.png")));
    public static final RegistryObject<Item> AZURE_SIGN_ITEM = ITEMS.register("azure_sign", () -> new AbnormalsSignItem(AZURE_SIGN.get(), AZURE_WALL_SIGN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Block> AZURE_LADDER = conditionallyRegisterBlock("azure_ladder", () -> new LadderBlock(Block.Properties.from(Blocks.LADDER)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> AZURE_LADDER_ITEM = conditionallyRegisterItem("azure_ladder", () -> new BlockItem(AZURE_LADDER.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> AZURE_BOOKSHELF = conditionallyRegisterBlock("azure_bookshelf", () -> new BookshelfBlock(Block.Properties.from(Blocks.OAK_PLANKS)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> AZURE_BOOKSHELF_ITEM = conditionallyRegisterItem("azure_bookshelf", () -> new BlockItem(AZURE_BOOKSHELF.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> AZURE_BEEHIVE = conditionallyRegisterBlock("azure_beehive", () -> new AbnormalsBeehiveBlock(Block.Properties.from(Blocks.OAK_PLANKS)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> AZURE_BEEHIVE_ITEM = conditionallyRegisterItem("azure_beehive", () -> new BlockItem(AZURE_BEEHIVE.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("buzzier_bees"));


    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> AZURE_CHEST = HELPER.createCompatChestBlocks("azure", MaterialColor.BLUE_TERRACOTTA, "quark");



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

    public static final RegistryObject<Block> HIMMEL_VERTICAL_SLAB = conditionallyRegisterBlock("himmel_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.PURPUR_BLOCK)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> HIMMEL_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("himmel_vertical_slab", () -> new BlockItem(HIMMEL_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> HIMMEL_STAIRS = BLOCKS.register("himmel_stairs", () -> new StairsBlock(HIMMEL_BLOCK.get().getDefaultState(), Block.Properties.from(Blocks.PURPUR_BLOCK)));
    public static final RegistryObject<Item> HIMMEL_STAIRS_ITEM = ITEMS.register("himmel_stairs", () -> new BlockItem(HIMMEL_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE = BLOCKS.register("violite", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_ITEM = ITEMS.register("violite", () -> new BlockItem(VIOLITE.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_BRICKS = BLOCKS.register("violite_bricks", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_BRICKS_ITEM = ITEMS.register("violite_bricks", () -> new BlockItem(VIOLITE_BRICKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_BRICK_SLAB = BLOCKS.register("violite_brick_slab", () -> new SlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_SLAB_ITEM = ITEMS.register("violite_brick_slab", () -> new BlockItem(VIOLITE_BRICK_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_BRICK_VERTICAL_SLAB = conditionallyRegisterBlock("violite_brick_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> VIOLITE_BRICK_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("violite_brick_vertical_slab", () -> new BlockItem(VIOLITE_BRICK_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> VIOLITE_STAIRS = BLOCKS.register("violite_brick_stairs", () -> new StairsBlock(VIOLITE_BRICKS.get().getDefaultState(), Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_STAIRS_ITEM = ITEMS.register("violite_brick_stairs", () -> new BlockItem(VIOLITE_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_BRICK_WALL = BLOCKS.register("violite_brick_wall", () -> new WallBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_WALL_ITEM = ITEMS.register("violite_brick_wall", () -> new BlockItem(VIOLITE_BRICK_WALL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_BRICKS = BLOCKS.register("mossy_violite_bricks", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_BRICKS_ITEM = ITEMS.register("mossy_violite_bricks", () -> new BlockItem(MOSSY_VIOLITE_BRICKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_BRICK_SLAB = BLOCKS.register("mossy_violite_brick_slab", () -> new SlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_SLAB_ITEM = ITEMS.register("mossy_violite_brick_slab", () -> new BlockItem(MOSSY_VIOLITE_BRICK_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_BRICK_VERTICAL_SLAB = conditionallyRegisterBlock("mossy_violite_brick_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> MOSSY_VIOLITE_BRICK_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("mossy_violite_brick_vertical_slab", () -> new BlockItem(MOSSY_VIOLITE_BRICK_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> MOSSY_VIOLITE_STAIRS = BLOCKS.register("mossy_violite_brick_stairs", () -> new StairsBlock(MOSSY_VIOLITE_BRICKS.get().getDefaultState(), Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_STAIRS_ITEM = ITEMS.register("mossy_violite_brick_stairs", () -> new BlockItem(MOSSY_VIOLITE_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_BRICK_WALL = BLOCKS.register("mossy_violite_brick_wall", () -> new WallBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_WALL_ITEM = ITEMS.register("mossy_violite_brick_wall", () -> new BlockItem(MOSSY_VIOLITE_BRICK_WALL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> CRACKED_VIOLITE_BRICKS = BLOCKS.register("cracked_violite_bricks", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> CRACKED_VIOLITE_BRICKS_ITEM = ITEMS.register("cracked_violite_bricks", () -> new BlockItem(CRACKED_VIOLITE_BRICKS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));


    public static final RegistryObject<Block> VIOLITE_TILES = BLOCKS.register("violite_tiles", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_TILES_ITEM = ITEMS.register("violite_tiles", () -> new BlockItem(VIOLITE_TILES.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_TILE_SLAB = BLOCKS.register("violite_tile_slab", () -> new SlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_TILE_SLAB_ITEM = ITEMS.register("violite_tile_slab", () -> new BlockItem(VIOLITE_TILE_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> VIOLITE_TILE_VERTICAL_SLAB = conditionallyRegisterBlock("violite_tile_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> VIOLITE_TILE_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("violite_tile_vertical_slab", () -> new BlockItem(VIOLITE_TILE_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> VIOLITE_TILE_STAIRS = BLOCKS.register("violite_tile_stairs", () -> new StairsBlock(VIOLITE_TILES.get().getDefaultState(), Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> VIOLITE_TILE_STAIRS_ITEM = ITEMS.register("violite_tile_stairs", () -> new BlockItem(VIOLITE_TILE_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_TILES = BLOCKS.register("mossy_violite_tiles", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_TILES_ITEM = ITEMS.register("mossy_violite_tiles", () -> new BlockItem(MOSSY_VIOLITE_TILES.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_TILE_SLAB = BLOCKS.register("mossy_violite_tile_slab", () -> new SlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_TILE_SLAB_ITEM = ITEMS.register("mossy_violite_tile_slab", () -> new BlockItem(MOSSY_VIOLITE_TILE_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MOSSY_VIOLITE_TILE_VERTICAL_SLAB = conditionallyRegisterBlock("mossy_violite_tile_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)), () -> isLoaded("quark"));
    public static final RegistryObject<Item> MOSSY_VIOLITE_TILE_VERTICAL_SLAB_ITEM = conditionallyRegisterItem("mossy_violite_tile_vertical_slab", () -> new BlockItem(MOSSY_VIOLITE_TILE_VERTICAL_SLAB.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)), () -> isLoaded("quark"));

    public static final RegistryObject<Block> MOSSY_VIOLITE_TILE_STAIRS = BLOCKS.register("mossy_violite_tile_stairs", () -> new StairsBlock(MOSSY_VIOLITE_TILES.get().getDefaultState(), Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> MOSSY_VIOLITE_TILE_STAIRS_ITEM = ITEMS.register("mossy_violite_tile_stairs", () -> new BlockItem(MOSSY_VIOLITE_TILE_STAIRS.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> CRACKED_VIOLITE_TILES = BLOCKS.register("cracked_violite_tiles", () -> new Block(Block.Properties.from(Blocks.NETHER_BRICKS).sound(SoundRegistry.VIOLITE_SOUND)));
    public static final RegistryObject<Item> CRACKED_VIOLITE_TILES_ITEM = ITEMS.register("cracked_violite_tiles", () -> new BlockItem(CRACKED_VIOLITE_TILES.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> ROSE_CRYSTAL = BLOCKS.register("rose_crystal", () -> new CrystalBlock(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.PINK).hardnessAndResistance(0.3F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().notSolid().setLightLevel((state) -> { return 7; })));
    public static final RegistryObject<Item> ROSE_CRYSTAL_ITEM = ITEMS.register("rose_crystal", () -> new BlockItem(ROSE_CRYSTAL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MINT_CRYSTAL = BLOCKS.register("mint_crystal", () -> new CrystalBlock(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.LIME).hardnessAndResistance(0.3F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().notSolid().setLightLevel((state) -> { return 7; })));
    public static final RegistryObject<Item> MINT_CRYSTAL_ITEM = ITEMS.register("mint_crystal", () -> new BlockItem(MINT_CRYSTAL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> COBALT_CRYSTAL = BLOCKS.register("cobalt_crystal", () -> new CrystalBlock(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.CYAN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().notSolid().setLightLevel((state) -> { return 7; })));
    public static final RegistryObject<Item> COBALT_CRYSTAL_ITEM = ITEMS.register("cobalt_crystal", () -> new BlockItem(COBALT_CRYSTAL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> ROSE_CRYSTAL_LAMP = BLOCKS.register("rose_crystal_lamp", () -> new Block(AbstractBlock.Properties.from(ROSE_CRYSTAL.get()).setLightLevel((state) -> { return 15; })));
    public static final RegistryObject<Item> ROSE_CRYSTAL_LAMP_ITEM = ITEMS.register("rose_crystal_lamp", () -> new BlockItem(ROSE_CRYSTAL_LAMP.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> MINT_CRYSTAL_LAMP = BLOCKS.register("mint_crystal_lamp", () -> new Block(AbstractBlock.Properties.from(MINT_CRYSTAL.get()).setLightLevel((state) -> { return 15; })));
    public static final RegistryObject<Item> MINT_CRYSTAL_LAMP_ITEM = ITEMS.register("mint_crystal_lamp", () -> new BlockItem(MINT_CRYSTAL_LAMP.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<Block> COBALT_CRYSTAL_LAMP = BLOCKS.register("cobalt_crystal_lamp", () -> new Block(AbstractBlock.Properties.from(COBALT_CRYSTAL.get()).setLightLevel((state) -> { return 15; })));
    public static final RegistryObject<Item> COBALT_CRYSTAL_LAMP_ITEM = ITEMS.register("cobalt_crystal_lamp", () -> new BlockItem(COBALT_CRYSTAL_LAMP.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    
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

    public static boolean isLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }


}
