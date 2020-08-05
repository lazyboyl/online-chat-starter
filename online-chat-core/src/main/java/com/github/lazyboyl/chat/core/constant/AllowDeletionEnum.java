package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzf
 * @since 2020/8/5
 * 类描述：
 */
public enum AllowDeletionEnum {
    /**
     * 允许删除
     */
    ALLOW_DELETE("0"),
    /**
     * 不允许删除
     */
    NOT_ALLOW("9");

    private String allowDeletion;

    AllowDeletionEnum(String allowDeletion) {
        this.allowDeletion = allowDeletion;
    }

    public String getAllowDeletion() {
        return allowDeletion;
    }
}
