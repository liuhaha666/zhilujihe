package org.javaboy.vhr.controller.exwaste;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Exwaste;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.service.ExwasteService;
import org.javaboy.vhr.utils.JsonData;
import org.javaboy.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exwaste")
public class ExwasteController {
    @Autowired
    ExwasteService exwasteService;
    @GetMapping("/basic")
    public Object getAllExwastesNum() {
        return JsonData.buildSuccess(exwasteService.getAllExwastesNum());
    }

    @GetMapping("/querystudentbypage")
    public RespPageBean getExwByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,Exwaste exwaste) {
        return exwasteService.getAllExwastesByPage(page, size,exwaste);
    }

    @PutMapping("/updateExw")
    public RespBean updateExw(@RequestBody Exwaste exwaste) {
        System.out.println("===exwaste update test===");
        System.out.println(exwaste);
        if (exwasteService.updateExw(exwaste) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id) {
        if (exwasteService.deleteExwByEid(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }


}

