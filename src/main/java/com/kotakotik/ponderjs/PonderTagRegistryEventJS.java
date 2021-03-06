package com.kotakotik.ponderjs;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.content.PonderTag;
import dev.latvian.kubejs.KubeJSRegistries;
import dev.latvian.kubejs.event.EventJS;
import dev.latvian.kubejs.util.ListJS;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PonderTagRegistryEventJS extends EventJS {
    public PonderTagRegistryEventJS create(String name, ResourceLocation displayItem, String title, String description, Object defaultItems) {
        try {
            ResourceLocation id = PonderJS.appendKubeToId(name);
            if (!PonderJS.tags.contains(id)) {
                PonderJS.tags.add(id);
            }
            if (!PonderJS.namespaces.contains(id.getNamespace())) {
                PonderJS.namespaces.add(id.getNamespace());
            }
            PonderTag tag = new PonderTag(id)
                    .item(KubeJSRegistries.items().get(displayItem))
                    .defaultLang(title, description);
            PonderRegistry.TAGS.listTag(tag);
            PonderJS.tagItemEvent.add(id.toString(), defaultItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return this;
    }

    public PonderTagRegistryEventJS remove(boolean clearItems, String... id) {
            List<ResourceLocation> res = Arrays.stream(id)
                    .map(PonderJS::appendCreateToId)
                    .collect(Collectors.toList());
            if (!PonderRegistry.TAGS.getListedTags()
                    .removeIf(tag -> {
                        if (res.contains(tag.getId())) {
                            if (clearItems) {
                                try {
                                    PonderJS.tagItemEvent.remove(tag.getId().toString(), ListJS.of(PonderRegistry.TAGS.getItems(tag)));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        }
                        return false;
                    })) {
                throw new NullPointerException("No tags found matching " + id);
            }
            return this;
    }

    public PonderTagRegistryEventJS remove(String... id) {
        return remove(true, id);
    }

    public PonderTagRegistryEventJS create(String name, ResourceLocation displayItem, String title, String description) {
        return create(name, displayItem, title, description, new ListJS());
    }
}
