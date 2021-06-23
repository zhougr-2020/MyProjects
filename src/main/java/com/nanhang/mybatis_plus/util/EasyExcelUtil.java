package com.nanhang.mybatis_plus.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.stereotype.Component;

/**
 * @author: immortal
 * @CreateDate: 2021/2/8 9:05
 * @Description:
 */
@Component
public class EasyExcelUtil extends AnalysisEventListener {
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
