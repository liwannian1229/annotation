package com.lwn.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.annotation.Generated;

/**
 * <p>
 *
 * </p>
 *
 * @author liwannian
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;
    /*AUTO：自增
　　　　NONE：该类型为未设置主键类型
　　　　INPUT：手动录入
　　　　ID_WORKER：默认主键类型，全局唯一ID，Long类型的主键
　　　　UUID：自动生成uuid 插入
　　　　ID_WORKER_STR：字符串全局唯一ID*/
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableLogic
    private Boolean isDel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
