package com.rx.oj.judge.codesandbox;


import com.rx.oj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.rx.oj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.rx.oj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据传入的参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     * @param type 沙箱类型
     * @return 沙箱实例
     */
    public static CodeSandbox newInstance(String type) {
        switch (type){
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }

}
