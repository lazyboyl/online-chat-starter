package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzef
 * @since 2020-08-04
 * 类描述： 申请状态的枚举类
 */
public enum ApplyStateEnum {

    /**
     * 拒绝
     */
    REFUSE("0"),
    /**
     * 待审核
     */
    AUDIT("1"),
    /**
     * 过期
     */
    EXPIRE("2"),
    /**
     * 审核通过
     */
    PASS("9");

    private String state;

    ApplyStateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
