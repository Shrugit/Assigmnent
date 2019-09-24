package com.gojek.assignment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Objects;
import java.util.Set;

public class JsonComparator implements IComparator<JsonElement, JsonElement> {

    @Override
    public boolean compare(JsonElement input1, JsonElement input2) {
        // If both nodes are null then return true
        if (Objects.isNull(input1) && Objects.isNull(input2)) {
            return true;
        }
        // If any one node null then return false
        if (Objects.isNull(input1) || Objects.isNull(input2)) {
            return false;
        }

        if (input1.isJsonNull() && input2.isJsonNull()) {
            return true;
        }

        if (input1.isJsonArray() && input2.isJsonArray()) {
            final JsonArray array1 = input1.getAsJsonArray();
            final JsonArray array2 = input2.getAsJsonArray();
            if (array1.size() != array2.size()) {
                return false;
            }
            final int len = array1.size();
            boolean isEqual;
            for (int i = 0; i < len; i++) {
                isEqual = compare(array1.get(i), array2.get(i));
                if (!isEqual) {
                    return false;
                }
            }
            return true;
        }

        if (input1.isJsonObject() && input2.isJsonObject()) {
            final Set<String> keySet1 = input1.getAsJsonObject().keySet();
            final Set<String> keySet2 = input2.getAsJsonObject().keySet();
            if (!keySet1.containsAll(keySet2) || keySet1.size() != keySet2.size()) {
                return false;
            }
            boolean isEqual;
            for (final String key : keySet1) {
                isEqual = compare(input1.getAsJsonObject().get(key), input2.getAsJsonObject().get(key));
                if (!isEqual) {
                    return false;
                }
            }
            return true;
        }

        if (!input1.isJsonPrimitive() || !input2.isJsonPrimitive()) {
            return false;
        }

        return input1.getAsJsonPrimitive().equals(input2.getAsJsonPrimitive());
    }

}
