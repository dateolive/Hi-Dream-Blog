package com.datealive.utils.markdowntoHtml.ext.cover.internal;

import com.datealive.utils.markdowntoHtml.ext.cover.Cover;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import java.util.Collections;
import java.util.Set;

/**
 * @ClassName: AbstractCoverNodeRenderer
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/27  9:53
 */
abstract class AbstractCoverNodeRenderer implements NodeRenderer {
    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Cover.class);
    }
}