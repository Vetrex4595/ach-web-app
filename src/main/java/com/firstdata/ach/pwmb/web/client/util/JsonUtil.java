package com.firstdata.ach.pwmb.web.client.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {

    public static String getJSONObject(Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        OutputStream stream = new BufferedOutputStream(byteStream);
        JsonGenerator jsonGenerator =
            objectMapper.getFactory().createGenerator(stream,
                                                              JsonEncoding.UTF8);
        // mapper.getSerializationConfig();
        objectMapper.writeValue(jsonGenerator, data);
        // mapper.writeValue(stream, payload);
        stream.flush();
        return new String(byteStream.toByteArray());
    }
    
    public static <Type> Type fromJson(String jsonInput,Class<Type> classType){
        Type obj = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(jsonInput,classType);
        }
        catch(JsonParseException parseException){
            parseException.printStackTrace();
            throw new RuntimeException(parseException.getMessage(), parseException);
        }
        catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage(), e);
        }
        return obj;
    }

	
}
