package com.kotakotik.ponderjs;

import com.kotakotik.ponderjs.commands.GenerateKubeJSLangCommand;
import com.kotakotik.ponderjs.commands.PJSPostCommand;
import com.kotakotik.ponderjs.commands.PJSReloadCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PJSCommands {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dis = event.getDispatcher();
        LiteralArgumentBuilder<CommandSource> b = Commands.literal(BuildConfig.MODID);
        if(ModList.get().isLoaded("kubejs")) {
            b.then(Commands.literal("kubejs")
                    .then(Commands.literal("reload").executes(
                            new PJSReloadCommand()
                    ))
                    .then(Commands.literal("generate_lang")
                            .requires((source) -> source.getServer().isSingleplayer())
                            .executes(new GenerateKubeJSLangCommand()))
                    .then(Commands.literal("postevents")
                            .executes(new PJSPostCommand()))
            );
        }
        dis.register(b);
    }
}
