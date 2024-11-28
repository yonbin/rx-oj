package com.rx.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rx.oj.annotation.AuthCheck;
import com.rx.oj.common.BaseResponse;
import com.rx.oj.common.ErrorCode;
import com.rx.oj.common.ResultUtils;
import com.rx.oj.constant.UserConstant;
import com.rx.oj.exception.BusinessException;
import com.rx.oj.model.dto.post.PostQueryRequest;
import com.rx.oj.model.dto.postthumb.PostThumbAddRequest;
import com.rx.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.rx.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.rx.oj.model.entity.Post;
import com.rx.oj.model.entity.QuestionSubmit;
import com.rx.oj.model.entity.User;
import com.rx.oj.model.vo.QuestionSubmitVO;
import com.rx.oj.service.QuestionSubmitService;
import com.rx.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交表
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question/submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取提交列表（除了管理员外、普通用户只能看到非答案、提交的代码等公开信息）
     *
     * @param postQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmit(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();

        // 查询出全部数据
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));

        final User loginUser = userService.getLoginUser(request);
        // 返回脱敏数据
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }
}
