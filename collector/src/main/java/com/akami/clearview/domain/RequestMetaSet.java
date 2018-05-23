package com.akami.clearview.domain;

import javax.json.Json;
import java.util.List;

/**
 * Created by akamikang on 2017. 7. 3..
 */
public interface RequestMetaSet {
    public void saveReqestMetaSet(Json requsestDataSetJson);
    public List getRuestSet();
}
