package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Exwaste;

import java.util.List;

public interface ExwasteMapper {
    List<Exwaste> getAllExwastes(@Param("page") Integer page, @Param("size") Integer size, @Param("exw") Exwaste exwaste);
    Long getAllExwastesNum();
    int deletecheckrow(@Param("id")int id);
    int updateByPrimaryKeySelective(Exwaste record);
    int deleteByPrimaryKey(Integer id);
}
