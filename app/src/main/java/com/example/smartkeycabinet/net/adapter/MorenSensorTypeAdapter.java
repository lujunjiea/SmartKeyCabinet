package com.example.smartkeycabinet.net.adapter;

import com.example.smartkeycabinet.model.MorenModel;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MorenSensorTypeAdapter extends TypeAdapter<MorenModel> {
    @Override
    public void write(JsonWriter out, MorenModel value) throws IOException {
        out.beginObject();
        //按自定义顺序输出字段信息
//        out.name("consumable_id").value(value.getConsumable_id());
//        out.name("container_id").value(value.getContainer_id());
//        out.name("container_index").value(value.getContainer_index());
//        out.name("status").value(value.getStatus());
//        out.name("quantity").value(value.getQuantity());
//        out.name("available").value(value.getAvailable());
        out.endObject();
    }

    @Override
    public MorenModel read(JsonReader in) throws IOException {
        return null;
    }
}
