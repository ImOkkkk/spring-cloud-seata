package com.imokkkk.order.discovery.client.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.http.MediaType;

/**
 * @author liuwy
 * @date 2023-07-13 14:07
 * @since 1.0
 */
public class ApiResponseDecoder implements Decoder {
    private final Decoder decoder;

    public ApiResponseDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type)
            throws IOException, DecodeException, FeignException {
        Collection<String> types =
                response.headers()
                        .getOrDefault(
                                "Content-Type", Arrays.asList("application/json;charset=UTF-8"));
        String contentType = types.iterator().next();
        MediaType mediaType = MediaType.parseMediaType(contentType);
        //自定义
        return decoder.decode(response, type);
    }
}
