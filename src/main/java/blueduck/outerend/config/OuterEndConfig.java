package blueduck.outerend.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OuterEndConfig {


    public ConfigHelper.ConfigValueListener<Integer> ENDER_DRAGON_HEALTH;
    public ConfigHelper.ConfigValueListener<Integer> AZURE_FOREST_WEIGHT;
    public ConfigHelper.ConfigValueListener<Integer> CRYSTAL_CRAG_WEIGHT;


    public OuterEndConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("General");

        this.ENDER_DRAGON_HEALTH= subscriber.subscribe(builder
                .comment("How Much Health should the Ender Dragon have? (Vanilla is 200)")
                .defineInRange("ender_dragon_health", 500, 1, 10000));
        this.AZURE_FOREST_WEIGHT= subscriber.subscribe(builder
                .comment("What should the weight of the Azure Forest be?")
                .defineInRange("azure_forest_weight", 10, 1, 1000));
        this.CRYSTAL_CRAG_WEIGHT= subscriber.subscribe(builder
                .comment("What should the weight of the Crystal Crag be?")
                .defineInRange("ender_dragon_health", 10, 1, 1000));

        builder.pop();
    }

}
