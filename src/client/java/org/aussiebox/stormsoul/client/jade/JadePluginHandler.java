package org.aussiebox.stormsoul.client.jade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.aussiebox.stormsoul.Stormsoul;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.util.CommonProxy;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JadePluginHandler implements IWailaPlugin {
    public static final Map<String, Supplier<Supplier<IWailaPlugin>>> PLUGIN_LOADERS = Maps.newHashMap();
    public static IWailaClientRegistration client;
    private final List<IWailaPlugin> plugins = Lists.newArrayList();

    static {
        PLUGIN_LOADERS.put(StormsoulJadePlugin.ID, () -> StormsoulJadePlugin::new);
    }

    public JadePluginHandler() {
        PLUGIN_LOADERS.forEach((modid, loader) -> {
            if (!CommonProxy.isModLoaded(modid)) {
                return;
            }
            try {
                plugins.add(loader.get().get());
            } catch (Throwable e) {
                Stormsoul.LOGGER.error("Failed to load Jade plugin for %s".formatted(modid), e);
            }
        });
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        plugins.removeIf($ -> {
            try {
                $.register(registration);
                return false;
            } catch (Throwable e) {
                Stormsoul.LOGGER.error("Failed to register Jade plugin %s".formatted($.getClass().getName()), e);
                return true;
            }
        });
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        plugins.forEach($ -> $.registerClient(registration));
    }
}
