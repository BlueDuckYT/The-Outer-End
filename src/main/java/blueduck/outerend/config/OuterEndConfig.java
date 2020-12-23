package blueduck.outerend.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OuterEndConfig {


    public ConfigHelper.ConfigValueListener<Integer> ENDER_DRAGON_HEALTH;


    public OuterEndConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("General");

        this.ENDER_DRAGON_HEALTH= subscriber.subscribe(builder
                .comment("How Much Health should the Ender Dragon have? (Vanilla is 200)")
                .defineInRange("ender_dragon_health", 500, 1, 10000));

        builder.pop();
    }

}
