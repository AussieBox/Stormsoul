package org.aussiebox.stormsoul.util;

public class StormsoulUtil {

    public static double smoothInterpolate(double start, double end, double time, boolean clamp) {
        if (clamp) time = Math.max(0.0, Math.min(1.0, time));
        double smoothT = time * time * (3 - 2 * time);
        return start + (end - start) * smoothT;
    }

}
