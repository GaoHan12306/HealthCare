package com.ivan.healthcare.healthcare_android.network;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现start()和startSync()方法的请求类
 * Created by Ivan on 16/1/30.
 */
public class BaseStringRequest extends AbsBaseRequest {

    private Call call;

    public BaseStringRequest() {
        super();
    }

    /**
     * 异步get请求
     * @param callback get请求回调
     */
    @Override
    public void get(final AbsBaseRequest.Callback callback) {
        try {
            call = OkHttpUtil.get(getURL(), getParams());
            OkHttpUtil.enqueue(call, new com.squareup.okhttp.Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    callback.onFailure(UNKNOEN_ERROR);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    callback.onResponse(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步post请求
     * @param callback post请求回调
     */
    @Override
    public void post(final AbsBaseRequest.Callback callback) {
        try {
            if (getFileType() == null) {
                call = OkHttpUtil.post(getURL(), getParams());
            } else {
                call = OkHttpUtil.postMultiPart(getURL(), getParams(), getFileType());
            }
            OkHttpUtil.enqueue(call, new com.squareup.okhttp.Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    callback.onFailure(UNKNOEN_ERROR);
                }

                @Override
                public void onResponse(Response response) {
                    callback.onResponse(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步post请求，切忌在UI线程调用
     * @return 请求结果的字符串，请求失败则会返回null
     * @throws IOException
     */
    @Override
    public String sync() throws IOException {
        if (getFileType() == null) {
            call = OkHttpUtil.post(getURL(), getParams());
        } else {
            call = OkHttpUtil.postMultiPart(getURL(), getParams(), getFileType());
        }
        return OkHttpUtil.execute(call);
    }

    @Override
    public void cancel() throws IOException {
        call.cancel();
    }

    /**
     * 构建网络请求BaseStringRequest类对象的Builder
     */
    public static class Builder {

        private Map<String,Object> params;
        private String url;
        private Map<String,MediaType> fileType;

        public Builder() {
            params = new HashMap<>();
            url = "";
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder add(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public Builder add(String key, Object file, MediaType type) {
            params.put(key, file);
            if (type == null) {
                return this;
            }
            if (fileType == null) {
                fileType = new HashMap<>();
            }
            fileType.put(key, type);
            return this;
        }

        public BaseStringRequest build() {
            BaseStringRequest request = new BaseStringRequest();
            request.setRequestParams(params);
            request.serRequestUrl(url);
            if (fileType != null) {
                request.setFileType(fileType);
            }
            return request;
        }
    }
}