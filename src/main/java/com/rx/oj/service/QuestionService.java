package com.rx.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rx.oj.model.dto.question.QuestionQueryRequest;
import com.rx.oj.model.entity.Question;
import com.rx.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题目服务
 *
 * @author gyb
 */
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question  题目实体
     * @param add  是否新增
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 题目查询条件
     * @return MyBatis-Plus 查询条件包装类
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装
     *
     * @param question 题目实体
     * @param request request
     * @return 题目VO
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage 题目分页
     * @param request request
     * @return  分页
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
