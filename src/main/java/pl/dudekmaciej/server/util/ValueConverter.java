package pl.dudekmaciej.server.util;

public class ValueConverter {
    public static Long toLong(double d) {
        return Long.valueOf(((Double)d).longValue());
    }

    public static Integer toInteger(double d) {
        return Integer.valueOf(((Double)d).intValue());
    }
}
