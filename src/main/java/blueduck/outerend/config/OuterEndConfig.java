package blueduck.outerend.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OuterEndConfig {


    public ConfigHelper.ConfigValueListener<Integer> ENDER_DRAGON_HEALTH;
    public ConfigHelper.ConfigValueListener<Integer> AZURE_FOREST_WEIGHT;
    public ConfigHelper.ConfigValueListener<Integer> CRYSTAL_CRAG_WEIGHT;

    public ConfigHelper.ConfigValueListener<Integer> END_TOWER_MIN;
    public ConfigHelper.ConfigValueListener<Integer> END_TOWER_MAX;
    public ConfigHelper.ConfigValueListener<Integer> CATACOMBS_MIN;
    public ConfigHelper.ConfigValueListener<Integer> CATACOMBS_MAX;


    public OuterEndConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("General");

        this.ENDER_DRAGON_HEALTH= subscriber.subscribe(builder
                .comment("How Much Health should the Ender Dragon have? Set to 0 to cancel the change. (Incase some other mod changes attributes) (Vanilla is 200)")
                .defineInRange("ender_dragon_health", 500, 0, 10000));
        this.AZURE_FOREST_WEIGHT= subscriber.subscribe(builder
                .comment("What should the weight of the Azure Forest be?")
                .defineInRange("azure_forest_weight", 10, 1, 1000));
        this.CRYSTAL_CRAG_WEIGHT= subscriber.subscribe(builder
                .comment("What should the weight of the Crystal Crag be?")
                .defineInRange("crystal_crag_weight", 10, 1, 1000));
        this.END_TOWER_MIN= subscriber.subscribe(builder
                .comment("What should the minimum distance (in chunks) be between End Towers")
                .defineInRange("tower_min", 15, 1, 1000));
        this.END_TOWER_MAX= subscriber.subscribe(builder
                .comment("What should the maximum distance (in chunks) be between End Towers")
                .defineInRange("tower_max", 45, 1, 1000));
        this.CATACOMBS_MIN= subscriber.subscribe(builder
                .comment("What should the minimum distance (in chunks) be between Catacombs")
                .defineInRange("catacombs_min", 15, 1, 1000));
        this.CATACOMBS_MAX= subscriber.subscribe(builder
                .comment("What should the maximum distance (in chunks) be between Catacombs")
                .defineInRange("catacombs_max", 45, 1, 1000));

        builder.pop();
    }

}
