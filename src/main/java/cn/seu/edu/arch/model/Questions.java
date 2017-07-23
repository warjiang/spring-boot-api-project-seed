package cn.seu.edu.arch.model;

import javax.persistence.*;

public class Questions {
    /**
     * question自增ID
     */
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    /**
     * 类型: type=0表示单选,type=1表示多选
     */
    private Integer type;

    /**
     * 试题学校来源
     */
    private String school;

    /**
     * 试题年份
     */
    private Integer year;

    /**
     * 一级分类
     */
    @Column(name = "categoryL1")
    private String categoryl1;

    /**
     * 二级目录 试题的属目录,1:中建,2外建,3:物理,4:构造,5:结构,6:现代
     */
    @Column(name = "categoryL2")
    private Integer categoryl2;

    /**
     * 三级目录 试题所属章节
     */
    @Column(name = "categoryL3")
    private Integer categoryl3;

    /**
     * 试题考点
     */
    private String point;

    /**
     * 试题难点
     */
    private String difficultpoint;

    /**
     * 试题
     */
    private String question;

    /**
     * 答案JSON
     */
    private String choices;

    /**
     * 正确答案
     */
    private String answer;

    /**
     * 试题解析
     */
    private String explanation;

    /**
     * 获取question自增ID
     *
     * @return question_id - question自增ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置question自增ID
     *
     * @param questionId question自增ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取类型: type=0表示单选,type=1表示多选
     *
     * @return type - 类型: type=0表示单选,type=1表示多选
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型: type=0表示单选,type=1表示多选
     *
     * @param type 类型: type=0表示单选,type=1表示多选
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取试题学校来源
     *
     * @return school - 试题学校来源
     */
    public String getSchool() {
        return school;
    }

    /**
     * 设置试题学校来源
     *
     * @param school 试题学校来源
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 获取试题年份
     *
     * @return year - 试题年份
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 设置试题年份
     *
     * @param year 试题年份
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 获取一级分类
     *
     * @return categoryL1 - 一级分类
     */
    public String getCategoryl1() {
        return categoryl1;
    }

    /**
     * 设置一级分类
     *
     * @param categoryl1 一级分类
     */
    public void setCategoryl1(String categoryl1) {
        this.categoryl1 = categoryl1;
    }

    /**
     * 获取二级目录 试题的属目录,1:中建,2外建,3:物理,4:构造,5:结构,6:现代
     *
     * @return categoryL2 - 二级目录 试题的属目录,1:中建,2外建,3:物理,4:构造,5:结构,6:现代
     */
    public Integer getCategoryl2() {
        return categoryl2;
    }

    /**
     * 设置二级目录 试题的属目录,1:中建,2外建,3:物理,4:构造,5:结构,6:现代
     *
     * @param categoryl2 二级目录 试题的属目录,1:中建,2外建,3:物理,4:构造,5:结构,6:现代
     */
    public void setCategoryl2(Integer categoryl2) {
        this.categoryl2 = categoryl2;
    }

    /**
     * 获取三级目录 试题所属章节
     *
     * @return categoryL3 - 三级目录 试题所属章节
     */
    public Integer getCategoryl3() {
        return categoryl3;
    }

    /**
     * 设置三级目录 试题所属章节
     *
     * @param categoryl3 三级目录 试题所属章节
     */
    public void setCategoryl3(Integer categoryl3) {
        this.categoryl3 = categoryl3;
    }

    /**
     * 获取试题考点
     *
     * @return point - 试题考点
     */
    public String getPoint() {
        return point;
    }

    /**
     * 设置试题考点
     *
     * @param point 试题考点
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * 获取试题难点
     *
     * @return difficultpoint - 试题难点
     */
    public String getDifficultpoint() {
        return difficultpoint;
    }

    /**
     * 设置试题难点
     *
     * @param difficultpoint 试题难点
     */
    public void setDifficultpoint(String difficultpoint) {
        this.difficultpoint = difficultpoint;
    }

    /**
     * 获取试题
     *
     * @return question - 试题
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 设置试题
     *
     * @param question 试题
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 获取答案JSON
     *
     * @return choices - 答案JSON
     */
    public String getChoices() {
        return choices;
    }

    /**
     * 设置答案JSON
     *
     * @param choices 答案JSON
     */
    public void setChoices(String choices) {
        this.choices = choices;
    }

    /**
     * 获取正确答案
     *
     * @return answer - 正确答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置正确答案
     *
     * @param answer 正确答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取试题解析
     *
     * @return explanation - 试题解析
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * 设置试题解析
     *
     * @param explanation 试题解析
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}