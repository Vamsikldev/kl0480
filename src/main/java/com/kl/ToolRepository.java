package com.kl;

import com.kl.domain.Tool;

import java.util.HashMap;
import java.util.Map;

public class ToolRepository {

    private static final Map<String, Tool> toolsDB = new HashMap<>();

    static {
        toolsDB.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        toolsDB.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        toolsDB.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        toolsDB.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    public Tool getTool(String toolCode) {
        return toolsDB.get(toolCode);
    }
}
