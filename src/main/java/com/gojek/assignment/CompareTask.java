package com.gojek.assignment;

import com.google.gson.JsonElement;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

public class CompareTask implements Callable<CompareTask> {

    private static final String OUTPUT = "%s %s %s";
    private static final String EQUALS = "equals";
    private static final String NOT_EQUALS = "not equals";

    private final String firstUrl;
    private final String secondUrl;
    private final RestService restService;
    private final JsonComparator jsonComparator;

    private boolean isEqual;

    public CompareTask(final String firstUrl, final String secondUrl) {
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        restService = new RestService();
        jsonComparator = new JsonComparator();
    }

    @Override
    public CompareTask call() throws Exception {
        // Assuming if any url is missing then its not equal
        if (StringUtils.isEmpty(firstUrl) || StringUtils.isEmpty(secondUrl)) {
            isEqual = false;
            return this;
        }
        // Continue if both url are present
        // removing extra space from url if it has in start and end of provided url by using trim function
        final JsonElement response1 = restService.doGetRequest(firstUrl.trim());
        final JsonElement response2 = restService.doGetRequest(secondUrl.trim());
        isEqual = jsonComparator.compare(response1, response2);
        return this;
    }

    public String getOutput() {
        return String.format(OUTPUT, firstUrl, isEqual? EQUALS : NOT_EQUALS, secondUrl);
    }

    public boolean isEqual() {
        return isEqual;
    }

}
