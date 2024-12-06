package com.rx.oj.judge;

import cn.hutool.json.JSONUtil;
import com.rx.oj.common.ErrorCode;
import com.rx.oj.exception.BusinessException;
import com.rx.oj.judge.codesandbox.CodeSandbox;
import com.rx.oj.judge.codesandbox.CodeSandboxFactory;
import com.rx.oj.judge.codesandbox.CodeSandboxProxy;
import com.rx.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rx.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.rx.oj.model.dto.question.JudgeCase;
import com.rx.oj.model.dto.questionsubmit.JudgeInfo;
import com.rx.oj.model.entity.Question;
import com.rx.oj.model.entity.QuestionSubmit;
import com.rx.oj.model.enums.JudgeInfoMessageEnum;
import com.rx.oj.model.enums.QuestionSubmitStatusEnum;
import com.rx.oj.model.vo.QuestionSubmitVO;
import com.rx.oj.service.QuestionService;
import com.rx.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;



    @Override
    public QuestionSubmitVO doJudge(long questionSubmitId) {
        //1）传入题目提交 Id，获取对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在！");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在！");
        }

        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中。");
        }

        //3）更改判题（题目提交）的状态为“判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目更新错误");
        }
        //4）调用沙箱，获取到执行的结果

        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();

        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse  executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        //5）根据沙箱的执行结果，设置题目的判题状态和信息
        // 先判断沙箱的执行结果输出数量是否和预期的数量相等
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        if(outputList.size()!= inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }

        // 依次判断每一项输出和预期的是否相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
           JudgeCase judgeCase = judgeCaseList.get(i);
           if(judgeCase.getOutput().equals(outputList.get(i))){
               judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
               return null;
           }
        }

        // 判断题目限制
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        String judgeConifgStr = question.getJudgeConfig();


        return null;
    }
}
