package com.rx.oj.judge.codesandbox;

import com.rx.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rx.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱定义
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
