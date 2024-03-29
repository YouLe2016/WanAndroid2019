/*
 * Copyright 2016 jeasonlzy(廖子尧)
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
package com.lzg.extend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleBaseResponse implements Serializable {

    private static final long serialVersionUID = -2L;

    @SerializedName(value = "errorCode", alternate = {"code", "status"})
    public int code;
    @SerializedName(value = "errorMsg", alternate = {"msg", "message"})
    public String msg;
    @SerializedName(value = "data")
    public String data;

    public BaseResponse toBaseResponse() {
        BaseResponse lzyResponse = new BaseResponse();
        lzyResponse.code = code;
        lzyResponse.data = data;
        lzyResponse.msg = msg;
        return lzyResponse;
    }
}
