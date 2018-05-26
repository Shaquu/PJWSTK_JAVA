package io.github.shaquu.memory.game.utils.transitions;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.scene.Node;

/**
 * A Transition that uses a Timeline internally and turns SPEED caching on for
 * the animated node during the animation.
 *
 * @author Jasper Potts
 */
class CachedTimelineTransition extends FXTransition {
    static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    /**
     * Create new CachedTimelineTransition
     *
     * @param node     The node that is being animated by the timeline
     * @param timeline The timeline for the animation, it should be from 0 to 1 seconds
     */
    CachedTimelineTransition(final Node node, final Timeline timeline) {
        this(node, timeline, true);
    }

    /**
     * Create new CachedTimelineTransition
     *
     * @param node     The node that is being animated by the timeline
     * @param timeline The timeline for the animation, it should be from 0 to 1 seconds
     * @param useCache When true the node is cached as image during the animation
     */
    private CachedTimelineTransition(final Node node, final Timeline timeline, final boolean useCache) {
        this.node = node;
        this.timeline = timeline;
        this.useCache = useCache;
        statusProperty().addListener((ov, t, newStatus) -> {
            switch (newStatus) {
                case RUNNING:
                    starting();
                    break;
                default:
                    stopping();
                    break;
            }
        });
    }
}