package com.github.lazyboyl.chat.core.constant;

/**
 * 类描述：群组的申请类型的枚举类
 *
 * @author linzef
 * @since 2020-08-06
 */
public enum GroupApplyType {
    /**
     * 邀请入群
     */
    INVITATION("1"),
    /**
     * 申请入群
     */
    APPLY("2");


    private String applyType;

    GroupApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyType() {
        return applyType;
    }
}
