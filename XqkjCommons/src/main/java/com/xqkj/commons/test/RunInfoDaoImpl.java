package com.xqkj.commons.test;


import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/27
 */
public class RunInfoDaoImpl implements RunInforDao {

    private Map<String,RunInforModel> modelMap=new HashMap<>();

    private List<String> keyList=new ArrayList<>();

    @Override
    public RunInforModel getByKey(String key) {
        return modelMap.get(key);
    }

    @Override
    public List<RunInforModel> queryList(RunInforQuery query) {
        List<RunInforModel> modelList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(keyList)){
            keyList.forEach(key->{
                if(modelMap.get(key)!=null){
                    modelList.add(modelMap.get(key));
                }
            });
        }
        return modelList;
    }

    @Override
    public void saveRunInforModel(RunInforModel model) {
        modelMap.put(model.getKey(),model);
        if(!keyList.contains(model.getKey())){
            keyList.add(model.getKey());
        }
    }

    @Override
    public boolean updateRunInforModel(RunInforModel model) {
        modelMap.put(model.getKey(),model);
        return true;
    }

    @Override
    public boolean deleteByKey(String key) {
        modelMap.remove(key);
        return false;
    }
}
