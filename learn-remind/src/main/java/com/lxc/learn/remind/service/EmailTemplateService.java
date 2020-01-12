package com.lxc.learn.remind.service;

import com.lxc.learn.remind.model.dao.RemindEmailTemplateMapper;
import com.lxc.learn.remind.model.entity.RemindEmailTemplate;
import com.lxc.learn.remind.model.entity.RemindEmailTemplateExample;
import com.lxc.learn.remind.model.entity.RemindEventToMeExample;
import com.lxc.learn.remind.util.BusinessException;
import com.lxc.learn.remind.util.DateUtil;
import com.lxc.learn.remind.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 14:46
 * @Description:
 */
@Service
public class EmailTemplateService {

    @Autowired
    private RemindEmailTemplateMapper remindEmailTemplateMapper;

    public RemindEmailTemplate getRandomEmailTemplate(String templateCode){
        RemindEmailTemplateExample example = new RemindEmailTemplateExample();
        example.createCriteria().andFEmailTemplateCodeEqualTo(templateCode);
        List<RemindEmailTemplate> list = remindEmailTemplateMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ReturnCode.FAIL);
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}
