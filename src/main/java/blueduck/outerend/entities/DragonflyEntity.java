package blueduck.outerend.entities;

import blueduck.outerend.client.Color;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class DragonflyEntity extends MobEntity {
	//using a separate navigator field then I should be using as I don't want this getting ticked by vanilla
	private final FlyingPathNavigator pathNavigator;
	
//	private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(HimmeliteEntity.class, DataSerializers.VARINT);
	
	public Color color = new Color(0,0,0);
	
	
	public DragonflyEntity(EntityType<? extends MobEntity> type, World worldIn) {
		super(type, worldIn);
		pathNavigator = new FlyingPathNavigator(this, this.world);
//		if (FMLEnvironment.dist.isClient()) {
//			setColor(getColor(world.getBiome(this.getPosition())));
//		}
	}
	
	public void setColor(int amt) {
		this.color = new Color(amt);
//		this.dataManager.register(COLOR, 1);
	}
	
	public int getColor() {
		return color.getRGB();
	}
	
	protected void registerData() {
		super.registerData();
	}
	
	public static AttributeModifierMap createModifiers() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.FLYING_SPEED, 1)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 0)
				.createMutableAttribute(Attributes.ARMOR, 0)
				.createMutableAttribute(Attributes.MAX_HEALTH, 10).create();
	}
	
	private BlockPos target = null;
	
	public boolean shouldRepathfind() {
		return this.ticksExisted % 1000 <= 10;
	}
	
	public Path getPath() {
		BlockPos pos = target;
		if (pos == null) {
			for (int i = 0; i <= 32; i++) {
				int x1 = (int) this.getPosX() + rand.nextInt(64) - 32;
				int z1 = (int) this.getPosZ() + rand.nextInt(64) - 32;
				int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x1, z1);
				boolean shouldFind = rand.nextBoolean();
				if (y == 0) continue;
				if (pos != null) {
					shouldFind = shouldFind &&
							(world.getBiome(new BlockPos(x1, y, z1)).getRegistryName().toString().equals("outer_end:azure_forest") ||
									!world.getBiome(pos).getRegistryName().toString().equals("outer_end:azure_forest"))
					;
					shouldFind = shouldFind || (
							world.getBiome(new BlockPos(x1, y, z1)).getRegistryName().toString().equals("outer_end:azure_forest") &&
									!world.getBiome(pos).getRegistryName().toString().equals("outer_end:azure_forest")
					);
				}
				if (shouldFind && pos == null)
					pos = new BlockPos(x1, y, z1);
				int searchDist = 8;
				for (int xOff = -searchDist; xOff <= searchDist; xOff++) {
					int x = x1 + xOff;
					for (int zOff = -searchDist; zOff <= searchDist; zOff++) {
						int z = z1 + zOff;
						int y1 = world.getHeight(Heightmap.Type.MOTION_BLOCKING, x1, z1)+1;
						if (world.getBlockState(new BlockPos(x, y1, z).down()).getBlock().equals(BlockRegistry.AZURE_STAMEN.get()))
							if (pos == null || y1 > pos.getY()) pos = new BlockPos(x, y1, z);
					}
				}
			}
			if (pos == null) {
				int x = (int) this.getPosX()+rand.nextInt(64)-32;
				int z = (int) this.getPosZ()+rand.nextInt(64)-32;
				int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
				pos = new BlockPos(x, Math.max(8,y), z);
			}
		}
//		pos = new BlockPos(-107, 6, 275);
		while (!world.getBlockState(pos).isAir())
			pos = pos.up();
		Path path = pathNavigator.getPathToPos(pos, 1);
//		ArrayList<BlockPos> positions = new ArrayList<>();
//		while (path != null && !path.reachesTarget()) {
//			path = navigator.pathfind(ImmutableSet.copyOf(positions), 1);
//			if (path != null && path.getFinalPathPoint() != null) {
//				positions.add(new BlockPos(
//						(int) path.getFinalPathPoint().x,
//						(int) path.getFinalPathPoint().y,
//						(int) path.getFinalPathPoint().z
//				));
//			} else break;
//		}
		target = pos;
		return path;
	}
	
	@Override
	public void tick() {
		if (this.getPosY() < 1) {
			this.damageEntity(DamageSource.OUT_OF_WORLD, 5);
		}
		if (this.getEntityWorld().isRemote || this.isAIDisabled()) {
			super.tick();
			if (FMLEnvironment.dist.isClient()) {
				Color thisColor = new Color(this.getColor());
				try {
					Color biomeColor = new Color(getColor(
							ForgeRegistries.BIOMES.getValue(
									world.func_242406_i(this.getPosition()).orElse(Biomes.PLAINS).getLocation()
							)
					));
						this.setColor(
							new Color(
									(int)MathHelper.lerp(0.1f,thisColor.getRed(),biomeColor.getRed()),
									(int)MathHelper.lerp(0.1f,thisColor.getGreen(),biomeColor.getGreen()),
									(int)MathHelper.lerp(0.1f,thisColor.getBlue(),biomeColor.getBlue())
							).getRGB()
					);
				} catch (Throwable ignored) {
				}
			}
			return;
		}
		this.fallDistance = 0;
		boolean isAboveBlock = world.getHeight(Heightmap.Type.MOTION_BLOCKING,this.getPosition()).getY() >= 4;
		if (
				((target == null || pathNavigator.getPath()==null || pathNavigator.getPath().isFinished()) && ((((shouldRepathfind() && this.onGround)||(this.getLastDamageSource()!=null))) || !isAboveBlock))
//						|| true
		) {
			pathNavigator.clearPath();
			pathNavigator.resetRangeMultiplier();
			pathNavigator.setPath(getPath(), 1);
		}
		if (!pathNavigator.hasPath()) this.target = null;
		this.setOnGround(pathNavigator.noPath());
		if (pathNavigator.getPath() != null && pathNavigator.getPath().getCurrentPathIndex() < pathNavigator.getPath().getCurrentPathLength() && target != null) {
			Vector3d targ = pathNavigator.getPath().getVectorFromIndex(this, pathNavigator.getPath().getCurrentPathIndex());
			Vector3d vector3d2 = new Vector3d(
					targ.getX(),
					targ.getY(),
					targ.getZ()
			).subtract(this.getPositionVec());
			Vector3d vector3d3 = new Vector3d(
					target.getX(),
					target.getY(),
					target.getZ()
			).subtract(this.getPositionVec());
			vector3d3 = vector3d3.mul(1,0,1);
			pathNavigator.tick();
			if (
					vector3d2.mul(1,0,1).squareDistanceTo(new Vector3d(0,0,0)) <=
					1
			) {
				this.onGround = true;
				Vector3d vector3d = new Vector3d(this.getMotion().x, Math.max(-0.1, this.getMotion().getY()-0.1f), this.getMotion().z);
				
				boolean reachedTarget = this.getPosition().offset(Direction.DOWN,this.getPosition().getY()).equals(pathNavigator.getTargetPos().offset(Direction.DOWN, pathNavigator.getTargetPos().getY()));
				if (this.getPosY() >= targ.getY() - 1) {
					if (!reachedTarget) pathNavigator.getPath().incrementPathIndex();
					if (reachedTarget || pathNavigator.getPath().getCurrentPathIndex() >= pathNavigator.getPath().getCurrentPathLength()) {
						if (this.getPosition().offset(Direction.DOWN,this.getPosition().getY()).distanceSq(new BlockPos(target.getX(),0,target.getZ())) >= 1) {
							pathNavigator.setPath(getPath(),1);
						} else {
							if (!this.world.getBlockState(this.getPosition().down()).isAir()) pathNavigator.clearPath();
							//TODO: figure out how to remove this and have it still work
//							this.setPosition(this.target.getX(),this.getPosY(),this.target.getZ());
							target = null;
						}
					}
					this.setMotion(vector3d);
				}
			}
			if (target != null) {
				this.onGround = false;
				boolean isAbove =
						(this.getPositionVec().y >=
								(
										target.getY()
													+ vector3d3.squareDistanceTo(new Vector3d(0, 0, 0))/128f
								) &&
								0 >= (vector3d2.getY()+1)
						);
//				System.out.println(vector3d2.getY()+10);
//				System.out.println(isAbove);
//				System.out.println(
//						!isAbove?Math.max(-0.1,this.getMotion().y+0.1f):1
//				);
				this.setMotion(
						MathHelper.lerp(0.1f, this.getMotion().x, vector3d2.normalize().x * 1.1f),
						!isAbove ?
								Math.max(-0.1,this.getMotion().y+0.1f) :
								MathHelper.lerp(0.2f, Math.max(-0.1, this.getMotion().getY()), -0.1f)
						,
						MathHelper.lerp(0.1f, this.getMotion().z, vector3d2.normalize().z * 1.1f)
				);
			}
		}
		if (this.getPosY() <= 3)
			this.setMotion(this.getMotion().x,MathHelper.lerp(0.1f,this.getMotion().y,-0.05f),this.getMotion().z);
		if (this.getPosY() <= 2.9)
			this.setMotion(this.getMotion().x,MathHelper.lerp(0.1f,this.getMotion().y,-0.01),this.getMotion().z);
		if (this.getPosY() <= 2.8)
			this.setMotion(this.getMotion().x,(this.getMotion().y+0.5f),this.getMotion().z);
		
		if (this.getLeashed()) {
			Vector3d pos = this.getLeashHolder().getPositionVec();
			if (this.getDistanceSq(pos) >= 20)
				this.setMotion(pos.subtract(this.getPositionVec()).scale(0.1f).add(this.getMotion()));
			if (this.getDistanceSq(pos) >= 256) this.clearLeashed(true,true);
		}
		
		super.tick();
	}
	
	public static int getColor(Biome biome) {
		if (biome == null || biome.getRegistryName() == null) {
			return 0;
		}
		if (
				biome.getRegistryName().toString().equals("minecraft:crimson_forest")
		) {
			return new Color(255,15,15).getRGB();
		} else if (
				biome.getRegistryName().toString().equals("minecraft:warped_forest")
		) {
			return new Color(19,112,118).getRGB();
		} else if (
				biome.getRegistryName().toString().equals("minecraft:nether_wastes")
		) {
			return new Color(100,16,16).getRGB();
		} else if (
				biome.getRegistryName().toString().equals("minecraft:soul_sand_valley")
		) {
			return new Color(41,35,34).getRGB();
		} else if (
				biome.getRegistryName().toString().equals("minecraft:flower_forest")
		) {
			return new Color(252,233,78).getRGB();
		} else if (
				biome.getRegistryName().toString().startsWith("minecraft:desert")
		) {
			return new Color(244,216,120).getRGB();
		} else if (
				biome.getCategory().equals(Biome.Category.THEEND) &&
						biome.getRegistryName().getNamespace().equals("minecraft")
		) {
			return new Color(255,255,255).getRGB();
		} else if (
				biome.getCategory().equals(Biome.Category.OCEAN) &&
						biome.getRegistryName().getNamespace().equals("minecraft")
		) {
			return new Color(89,115,165).getRGB();
		} else if (
				biome.getRegistryName().toString().equals("minecraft:basalt_deltas")
		) {
			return new Color(115,120,128).getRGB();
		}
		return biome.getGrassColor(0,0);
	}
	
	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemRegistry.SPECTRAFLY_SPAWN_EGG.get());
	}

	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_BEE_HURT;
	}


	public boolean makeFlySound() {
		return true;
	}

	public static boolean canSpawn(EntityType<DragonflyEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return (pos.getY() > 50);
	}
}