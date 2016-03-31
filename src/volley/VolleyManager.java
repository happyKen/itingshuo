package volley;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import volley.App;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Map;

import okhttp3.OkHttpClient;


public class VolleyManager {
    private static VolleyManager mVolleyManager = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * @param context
     */
    public VolleyManager(Context context) {

    //    mRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(new OkHttpClient()));

        mRequestQueue = Volley.newRequestQueue(context);

        mImageLoader = new ImageLoader(mRequestQueue,
                new LruBitmapCache(context));
    }

    /**
     * @return VolleyManager instance
     */
    public static synchronized VolleyManager newInstance() {
        if (mVolleyManager == null) {
            mVolleyManager = new VolleyManager(App.getContext());
        }
        return mVolleyManager;
    }

    private <T> Request<T> add(Request<T> request) {
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * @param tag
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public StringRequest StrRequest(Object tag, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * @param tag
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public StringRequest StrRequest(Object tag, int method, String url, Response.Listener<String> listener,
                                    Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(method, url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * ImageRequest
     *
     * @param tag
     * @param url
     * @param listener
     * @param maxWidth
     * @param maxHeight
     * @param scaleType
     * @param decodeConfig
     * @param errorListener
     * @return
     */
    public ImageRequest ImageRequest(Object tag, String url, Response.Listener<Bitmap> listener,
                                     int maxWidth, int maxHeight, ImageView.ScaleType scaleType,
                                     Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        ImageRequest request = new ImageRequest(url, listener, maxWidth, maxHeight, scaleType,
                decodeConfig, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * ImageLoader
     *
     * @param imageView
     * @param imgViewUrl
     * @param defaultImageResId
     * @param errorImageResId
     */
    public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
                                   int errorImageResId) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
                errorImageResId);
        mImageLoader.get(imgViewUrl, listener);
    }


    /**
     * ImageLoader
     * ImageLoader指定图片大小
     * @param imageView
     * @param imgViewUrl
     * @param defaultImageResId
     * @param errorImageResId
     * @param maxWidth
     * @param maxHeight
     */
    public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
                                   int errorImageResId, int maxWidth, int maxHeight) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
                errorImageResId);
        mImageLoader.get(imgViewUrl, listener, maxWidth, maxHeight);
    }

    /**
     * Get
     *
     * @param tag
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonGetRequest(Object tag, String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * Post
     *
     * @param tag
     * @param params
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonPostRequest(Object tag, Map<String, String> params, String url, Class<T> clazz, Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
