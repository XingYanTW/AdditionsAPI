package us.fihgu.toolbox.reflection;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

public class ReflectionUtils {
	final public static String NMSPath = "net.minecraft.server.";
	final public static String craftbukkitPath = "org.bukkit.craftbukkit.";

	/**
	 * gets the current version of minecraft.
	 */
    public static String getMinecraftVersion() {
        String version = Bukkit.getVersion();  // For Paper and Spigot servers
        // Extract version number (e.g., "1.21.6")
        if (version.contains("MC:")) {
            return version.split("MC:")[1].split(" ")[0].trim();
        } else {
            Bukkit.getLogger().warning("Unable to determine Minecraft version from getVersion: " + version);
            return "unknown"; // Fallback value
        }
    }

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String className) {
        String version = getMinecraftVersion();
        if ("unknown".equals(version)) {
            Bukkit.getLogger().warning("Unable to find NMS class because Minecraft version is unknown.");
            return null;
        }
        return getClass(NMSPath + version + "." + className);
    }

    public static Class<?> getCraftBukkitClass(String className) {
        String version = getMinecraftVersion();
        if ("unknown".equals(version)) {
            Bukkit.getLogger().warning("Unable to find CraftBukkit class because Minecraft version is unknown.");
            return null;
        }
        return getClass(craftbukkitPath + version + "." + className);
    }


    public static Field findUnderlyingField(Class<?> clazz, String fieldName) {
		Class<?> current = clazz;
		do {
			try {
				return current.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		} while ((current = current.getSuperclass()) != null);
		return null;
	}
}
