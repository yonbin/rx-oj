package com.rx.oj.judge.codesandbox.model;

import com.rx.oj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;
    private String message;
    private Integer status;
    private JudgeInfo judgeInfo;
}
