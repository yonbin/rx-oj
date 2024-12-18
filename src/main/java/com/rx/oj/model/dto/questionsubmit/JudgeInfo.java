package com.rx.oj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 题目判题信息
 * @author gyb
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗内存（KB）
     */
    private Long memory;
    /**
     * 消耗时间
     */
    private  Long time;
}
