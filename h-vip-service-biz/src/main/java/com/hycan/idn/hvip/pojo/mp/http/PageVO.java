package com.hycan.idn.hvip.pojo.mp.http;

import org.springframework.data.domain.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 分页实体类
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "分页实体类")
public class PageVO<T> {

    /**
     * 分页数据
     */
    @ApiModelProperty("分页数据")
    private List<T> records;

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private Integer total;

    /**
     * 每页显示的记录数
     */
    @ApiModelProperty("每页显示记录数")
    private Integer size;

    /**
     * 当前页码值
     */
    @ApiModelProperty("当前页码值")
    private Integer current;

    public static <T> PageVO<T> of(Page<T> page) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(page.getContent());
        pageVO.setCurrent(page.getNumber());
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotalPages());
        return pageVO;
    }
}
