package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.ExwasteMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Exwaste;
import org.javaboy.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExwasteService {
    @Autowired
    ExwasteMapper exwasteMapper;

    public RespPageBean getAllExwastesByPage(Integer page, Integer size,Exwaste exwaste) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Exwaste> data = exwasteMapper.getAllExwastes(page, size,exwaste);
        Long total =  exwasteMapper.getAllExwastesNum();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        System.out.println(bean);
        return bean;
    }

    public Long getAllExwastesNum() {
        Long rownums = exwasteMapper.getAllExwastesNum();
        return rownums;
    }

    public Integer updateExw(Exwaste exwaste) {
        return exwasteMapper.updateByPrimaryKeySelective(exwaste);
    }

    public Integer deleteExwByEid(Integer id) {
        return exwasteMapper.deleteByPrimaryKey(id);
    }

}

