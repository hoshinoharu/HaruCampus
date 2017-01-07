package com.haru.tools;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

/**
 * Created by 星野悠 on 2017/1/6.
 */

public class GlideTool {
    public static GlideUrl getUrlWithCookies(String url, String cookies){
        GlideUrl cookie = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", cookies).build());
        return cookie ;
    }
}
