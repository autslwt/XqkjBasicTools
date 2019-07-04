package com.xqkj.commons.progress.impl;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 10:35 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefRedisRunInforDao implements RunInforDao{

    private StringRedisTemplate stringRedisTemplate;
    /**
     * 2d
     */
    private static Long expiredTimes=24*3600*2L;

    private int maxListSize=10;

    @Override
    public RunInforModel getByKey(String key) {
        String runInfoJson=stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(runInfoJson)){
            return JSON.parseObject(runInfoJson,RunInforModel.class);
        }
        return null;
    }

    /**
     * 这个方法会返回固定长度的数据，并且只用type字段为有效值
     * @param query
     * @return
     */
    @Override
    public List<RunInforModel> queryList(RunInforQuery query) {
        List<String> keyList = stringRedisTemplate.opsForList().range(query.getType(),0,maxListSize-1);
        List<RunInforModel> modelList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(keyList)){
            keyList.forEach(key->{
                RunInforModel runInforModel=getByKey(key);
                if(runInforModel!=null){
                    modelList.add(runInforModel);
                }
            });
        }
        return modelList;
    }

    @Override
    public void saveRunInforModel(RunInforModel model) {
        stringRedisTemplate.opsForValue().set(model.getKey(), JSON.toJSONString(model),expiredTimes, TimeUnit.SECONDS);
        if(StringUtils.isNotBlank(model.getType())){
            List<String> keyList = stringRedisTemplate.opsForList().range(model.getType(),0,maxListSize-1);
            if(CollectionUtils.isEmpty(keyList)){
                stringRedisTemplate.opsForList().leftPush(model.getType(),model.getKey());
            }else if(!keyList.contains(model.getType())){
                Long rsLong=stringRedisTemplate.opsForList().leftPush(model.getType(),model.getKey());
                if(rsLong>maxListSize){
                    stringRedisTemplate.opsForList().trim(model.getType(),0,maxListSize-1);
                }
            }
        }
    }

    @Override
    public boolean updateRunInforModel(RunInforModel model) {
        stringRedisTemplate.opsForValue().set(model.getKey(), JSON.toJSONString(model),expiredTimes,TimeUnit.SECONDS);
        return true;
    }

    /**
     * 请不要调用该操作，不支持
     * @param key
     * @return
     */
    @Override
    public boolean deleteByKey(String key) {
        throw new UnsupportedOperationException();
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }
}
