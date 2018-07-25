package com.entertainment.common.utils;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CacheUtils
 *  @package: com.entertainment.common.utils
 *  @Date: Created in 2018/7/25 下午3:57
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public class CacheUtils {

    private static final String KEY_PREFIX = "KEY";
    private static final String ID_PREFIX = "ID";
    private static final String COLON = ":";

    /**
     * 生成IP的缓存的Key
     *
     * @param id
     * @return
     */
    public static String genIdKey(Long id) {
        return genCustomKey(ID_PREFIX, id.toString());
    }

    /**
     * 生成Key的缓存的Key
     *
     * @param key
     * @return
     */
    public static String genKeyKey(String key) {
        return genCustomKey(KEY_PREFIX, key);
    }

    /**
     * 生成自定义的KEY， seg和key之间用","隔开
     *
     * @param seg
     * @param key
     * @return
     */
    public static String genCustomKey(String seg, String key) {
        StringBuilder sb = new StringBuilder();
        if (Preconditions.isNotBlank(seg)) {
            sb.append(seg).append(COLON);
        }
        sb.append(key);
        return sb.toString();
    }

    /**
     * 生成不带前缀的缓存的Key
     *
     * @param key
     * @return
     */
    public static String genCustomKey(String key) {
        return genCustomKey(null, key);
    }

}
