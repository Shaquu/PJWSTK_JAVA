/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.utils.transitions;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.util.Duration;

public class FXTransition extends Transition {

    Timeline timeline;
    boolean useCache = false;
    Node node = null;
    private CacheHint oldCacheHint = CacheHint.DEFAULT;
    private boolean oldCache = false;

    FXTransition() {
    }

    @Override
    protected void interpolate(double frac) {
        timeline.playFrom(Duration.seconds(frac));
        timeline.stop();
    }

    /**
     * Called when the animation is starting
     */
    void starting() {
        if (useCache) {
            oldCache = node.isCache();
            oldCacheHint = node.getCacheHint();
            node.setCache(true);
            node.setCacheHint(CacheHint.SPEED);
        }
    }

    /**
     * Called when the animation is stopping
     */
    void stopping() {
        if (useCache) {
            node.setCache(oldCache);
            node.setCacheHint(oldCacheHint);
        }
    }
}
