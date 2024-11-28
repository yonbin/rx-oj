package com.rx.oj.model.dto.question;

import lombok.Data;

/**
 * @author gyb
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（KB）
     */
    private Long timeLimit;
    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
