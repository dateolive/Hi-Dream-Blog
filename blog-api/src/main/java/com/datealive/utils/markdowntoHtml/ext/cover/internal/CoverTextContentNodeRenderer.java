package com.datealive.utils.markdowntoHtml.ext.cover.internal;

import org.commonmark.node.Node;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentWriter;
/**
 * @ClassName: CoverTextContentNodeRenderer
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/27  9:56
 */
public class CoverTextContentNodeRenderer extends AbstractCoverNodeRenderer {
    private final TextContentNodeRendererContext context;
    private final TextContentWriter textContent;

    public CoverTextContentNodeRenderer(TextContentNodeRendererContext context) {
        this.context = context;
        this.textContent = context.getWriter();
    }

    @Override
    public void render(Node node) {
        textContent.write('/');
        renderChildren(node);
        textContent.write('/');
    }

    private void renderChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }
}