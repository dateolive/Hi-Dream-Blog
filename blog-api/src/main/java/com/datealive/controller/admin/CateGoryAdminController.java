package com.datealive.controller.admin;


import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.service.CateGoryService;
import com.datealive.service.vo.CateGory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: CateGoryAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/2  21:17
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class CateGoryAdminController {

    @Autowired
    CateGoryService cateGoryService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "添加分类",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveCategory")
    public Result saveCategory(@Validated @RequestBody CateGory cateGory){
        boolean saveCategory = cateGoryService.saveCategory(cateGory);
        if(saveCategory){
            return Result.success("添加分类成功",cateGory.getCategory_id());
        }else{
            return Result.error("添加分类失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改分类",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateCategory")
    public Result updateCategory(@Validated @RequestBody CateGory cateGory){
        boolean updateCategory = cateGoryService.updateCategory(cateGory);
        if(updateCategory){
            return Result.success("修改分类成功");
        }else{
            return Result.error("修改分类失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除分类",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/deleteCategory")
    public Result deleteCategoryById(@RequestParam Integer category_id){
        //删除某个分类的时候，需要将该分类与文章的分类联系表也删除掉，防止数据出现错误，这里需要开启事务
        boolean deleteCategory = cateGoryService.deleteCategory(category_id);
        if(deleteCategory){
            log.info("删除分类成功");
            return Result.success("删除分类成功");
        }else{
            log.error("删除分类失败");
            return Result.error("删除分类失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/categoryList")
    public PageResult<CateGory> getCateGoryByPage(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<CateGory> cateGoryByPage = cateGoryService.getCateGoryByPage(currentPage);
        return cateGoryByPage;
    }

}
