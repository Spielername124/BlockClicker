package me.Spielername124.blockClicker.Helper;

import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Chance;

import java.util.Collections;
import java.util.Map;

public final class MapParser {

    // Private constructor prevents instantiation
    private MapParser() {}

    public static String getString(Map<?, ?> map, String key, String defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        return val != null ? val.toString() : defaultValue;
    }

    public static int getInt(Map<?, ?> map, String key, int defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        if (val instanceof Number num) {
            return num.intValue();
        }
        if (val instanceof String str) {
            try { return Integer.parseInt(str.trim()); } catch (NumberFormatException ignored) {}
        }
        return defaultValue;
    }

    public static double getDouble(Map<?, ?> map, String key, double defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        if (val instanceof Number num) {
            return num.doubleValue();
        }
        if (val instanceof String str) {
            try { return Double.parseDouble(str.trim()); } catch (NumberFormatException ignored) {}
        }
        return defaultValue;
    }

    public static float getFloat(Map<?, ?> map, String key, float defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        if (val instanceof Number num) {
            return num.floatValue();
        }
        if (val instanceof String str) {
            try { return Float.parseFloat(str.trim()); } catch (NumberFormatException ignored) {}
        }
        return defaultValue;
    }

    public static boolean getBoolean(Map<?, ?> map, String key, boolean defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        if (val instanceof Boolean bool) {
            return bool;
        }
        if (val instanceof String str) {
            return Boolean.parseBoolean(str.trim());
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static Map<?, ?> getMap(Map<?, ?> map, String key) {
        if (map == null) return Collections.emptyMap();
        Object val = map.get(key);
        if (val instanceof Map<?, ?> nested) {
            return nested;
        }
        return Collections.emptyMap();
    }

    public static Chance.LuckModifierDependence getLuckModifierDependence(Map<?, ?> map, String key, Chance.LuckModifierDependence defaultValue) {
        if (map == null) return defaultValue;
        Object val = map.get(key);
        if (val == null) return defaultValue;

        try {
            return Chance.LuckModifierDependence.valueOf(val.toString().trim().toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return defaultValue;
        }
    }
}