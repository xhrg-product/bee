package com.github.xhrg.bee.gateway.bak;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cir {
    public static void main(String[] args) throws Exception {
        String key = "key";

        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        // set limit exception ratio to 0.1
        // 将比例设置成0.6将全部通过, exception_ratio = 异常/通过量
        // 当资源的每秒异常总数占通过量的比值超过阈值（DegradeRule 中的 count）之后，资源进入降级状态
        rule.setCount(0.4);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);

        while (true) {
            Thread.sleep(100);
            Entry entry = null;
            try {
                entry = SphU.entry(key, EntryType.IN);
                System.out.println("正常业务");
                // Write your biz code here.
                // <<BIZ CODE>>
                int i = new Random().nextInt(10);
                if (i > 3) {
                    i = i / 0;
                }
            } catch (Throwable t) {
                System.out.println("异常" + t.getClass());
                if (!BlockException.isBlockException(t)) {
                    Tracer.trace(t);
                }
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

}

