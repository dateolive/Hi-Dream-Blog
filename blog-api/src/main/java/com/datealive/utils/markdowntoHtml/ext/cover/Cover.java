package com.datealive.utils.markdowntoHtml.ext.cover;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;

/**
 * @ClassName: Cover
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/27  9:51
 */
public class Cover extends CustomNode implements Delimited {
    private static final String DELIMITER = "%%";

    @Override
    public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override
    public String getClosingDelimiter() {
        return DELIMITER;
    }
}