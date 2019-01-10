package io.flutter.plugins;


import io.flutter.plugin.common.PluginRegistry;
import io.github.ponnamkarthik.toast.fluttertoast.FluttertoastPlugin;

/**
 * Generated file. Do not edit.
 */
public final class GeneratedPluginRegistrant {
    public static void registerWith(PluginRegistry registry) {
        if (alreadyRegisteredWith(registry)) {
            return;
        }
        FluttertoastPlugin.registerWith(registry.registrarFor(FluttertoastPlugin.CHANNEL_NAME));
        FlutterRouterMethodPlugin.registerWith(registry.registrarFor(FlutterRouterMethodPlugin.CHANNEL_NAME));
//        FlutterRefreshEventPlugin.registerWith(registry.registrarFor(FlutterRefreshEventPlugin.CHANNEL_NAME));
    }

    private static boolean alreadyRegisteredWith(PluginRegistry registry) {
        final String key = GeneratedPluginRegistrant.class.getCanonicalName();
        if (registry.hasPlugin(key)) {
            return true;
        }
        registry.registrarFor(key);
        return false;
    }
}
