package com.rx.oj.model.vo;

import cn.hutool.json.JSONUtil;
import com.rx.oj.model.dto.questionsubmit.JudgeInfo;
import com.rx.oj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 程序语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息(json 对象）
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态(0- 待判题、1- 判题中、2- 成功、3- 失败)
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;


    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (null == questionSubmitVO) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        if (null != judgeInfo) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }

        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (null == questionSubmit) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);

        JudgeInfo judgeInfo = JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class);
        questionSubmitVO.setJudgeInfo(judgeInfo);

        return questionSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}