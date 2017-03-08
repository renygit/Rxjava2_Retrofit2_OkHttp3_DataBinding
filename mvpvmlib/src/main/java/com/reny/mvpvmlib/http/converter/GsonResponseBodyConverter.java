/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reny.mvpvmlib.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.reny.mvpvmlib.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        LogUtils.d(body);
        try {
            return adapter.fromJson(body);
        } finally {
            value.close();
        }
        /*if(isHandlerError) {
            HttpBaseModel model = gson.fromJson(body, BaseModel.class);
            try {
                if (!model.isError()) {
                    return adapter.fromJson(body);
                } else {
                    throw new ResultErrorException(-1, "未知异常");
                }
            } finally {
                value.close();
            }
        }else {
            try {
                return adapter.fromJson(body);
            } finally {
                value.close();
            }
        }*/
    }
}
