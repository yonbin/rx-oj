package com.rx.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rx.oj.model.dto.question.QuestionQueryRequest;
import com.rx.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.rx.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.rx.oj.model.entity.Question;
import com.rx.oj.model.entity.QuestionSubmit;
import com.rx.oj.model.entity.User;
import com.rx.oj.model.vo.QuestionSubmitVO;
import com.rx.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author gyb
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
     long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return MyBatis-Plus 查询条件包装类
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit 题目实体
     * @param request request
     * @return 题目VO
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage 题目分页
     * @param request request
     * @return  分页
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
