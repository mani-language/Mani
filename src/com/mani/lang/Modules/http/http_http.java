package com.mani.lang.Modules.http;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
/*import java.util.Map;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;*/

public final class http_http implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        /*try {
            final Response response = client.newCall(
                    new Request.Builder().url(arguments.get(0).toString()).build())
                    .execute()
            return response.body().bytes();
        } catch (IOException ex) {
            return ex;
        }*/
        return null;
    }
}
