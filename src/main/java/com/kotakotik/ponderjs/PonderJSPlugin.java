package com.kotakotik.ponderjs;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraftforge.api.distmarker.Dist;

import static net.minecraftforge.fml.DistExecutor.safeRunWhenOn;
import static net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn;

public class PonderJSPlugin extends KubeJSPlugin {
    public PonderJSPlugin() {
        safeRunWhenOn(Dist.CLIENT, () -> PonderJS::clientPluginInit);
    }

    @Override
    public void addBindings(BindingsEvent event) {
        unsafeRunWhenOn(Dist.CLIENT, () -> () -> PonderJS.addBindings(event));
    }

    @Override
    public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        if (type != ScriptType.CLIENT) return;
        unsafeRunWhenOn(Dist.CLIENT, () -> () -> PonderJS.addTypeWrappers(type, typeWrappers));
    }
}
