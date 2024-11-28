package com.rx.oj.judge.codesandbox.impl;

import com.rx.oj.judge.codesandbox.CodeSandbox;
import com.rx.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rx.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 示例代码沙箱：仅为跑通业务流程。
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("例代码沙箱");
        return null;
    }
}
