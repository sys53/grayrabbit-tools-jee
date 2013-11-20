package com.youlema.tools.jee.url;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youlema.tools.jee.enums.URLMappingEnum;

/**
 * URL Rewrite class
 * <p/>
 * User: liyd
 * Date: 13-4-1 下午7:18
 * version $Id: URLRewriteUtil.java, v 0.1 Exp $
 */
public final class URLRewriteUtil {

    /** log object */
    private static final Logger     LOG               = LoggerFactory
                                                          .getLogger(URLRewriteUtil.class);
    /** is enable */
    private static boolean          enable            = true;

    /** original url matches regular */
    public static final String      ORIG_URL_REGEX    = "/(\\w+/)*(\\w+)\\.action([?]{1}[\\w|.]*[=][\\w\\u0391-\\uFFE5]*)?(&[\\w|.]*[=][\\w\\u0391-\\uFFE5]*)*";

    /** rewrite url matches regular */
    public static final String      REWRITE_URL_REGEX = "/([\\w\\u0391-\\uFFE5]+[-]?)*\\.html";

    /** url rewrite configuration properties file */
    private static final String     REWRITE_CONF_FILE = "urlrewrite.properties";

    /** The constant urlMappingList. */
    private static List<URLMapping> urlMappingList    = new ArrayList<URLMapping>();

    /** The constant ACTION_PREFIX. */
    private static final String     ACTION_PREFIX     = "action";

    /** The constant PREFIX_DEFINITION. */
    private static final String     PREFIX_DEFINITION = "prefix";

    /** The constant PARAM_PREFIX. */
    private static final String     PARAM_PREFIX      = "param";

    /** rewrite url parameters delimiters */
    private static final String     PARAM_SEPARATOR   = "-";

    /** indicates that the parameter has no value. */
    private static final String     PARAM_NO_VALUE    = "0000";

    /** rewrite url suffix */
    private static final String     URL_SUFFIX        = ".html";

    static {

        try {

            LOG.info("Initialization url rewrite configuration,Reads the configuration file {}",
                REWRITE_CONF_FILE);

            Properties properties = new Properties();

            InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(REWRITE_CONF_FILE);

            properties.load(is);

            String isEnable = properties.getProperty("enable");

            if (!StringUtils.equalsIgnoreCase("true", isEnable)) {
                enable = false;
            }

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {

                String key = String.valueOf(entry.getKey());

                if (StringUtils.indexOf(key, '.') == -1) {
                    continue;
                }

                String[] keys = StringUtils.split(key, '.');

                if (keys.length != 3 && keys.length != 4) {

                    throw new RuntimeException("Defined key [" + key
                                               + "] is error,Please check it.");
                }

                String value = String.valueOf(entry.getValue());

                URLMapping urlMapping = new URLMapping();
                urlMapping.setKey(key);
                urlMapping.setValue(value);
                urlMapping.setSplitKey(keys);

                if (StringUtils.equals(keys[1], ACTION_PREFIX)) {

                    //get system name
                    String system = properties.getProperty(keys[0]);

                    urlMapping.setSystemKey(keys[0]);
                    urlMapping.setSystemValue(system);
                    urlMapping.setFinalValue(system + value);
                }

                urlMappingList.add(urlMapping);
            }

            LOG.info("Initialization url rewrite configuration successfully");
        } catch (IOException e) {
            throw new RuntimeException(
                "Initialization url rewrite configuration failure,Please check the configuration file 'urlrewrite.properties'.");
        }

    }

    /**
     * private constructor
     */
    private URLRewriteUtil() {
    }

    /**
     * Get rewrite url.
     *
     * @param origUrl the orig url
     * @return the rewrite url
     */
    public static String getRewriteUrl(String origUrl) {

        return regReplace(origUrl, ORIG_URL_REGEX);
    }

    /**
     * Get orig url.
     *
     * @param rewriteUrl the rewrite url
     * @return the orig url
     */
    public static String getOrigUrl(String rewriteUrl) {

        return regReplace(rewriteUrl, REWRITE_URL_REGEX);
    }

    /**
     * replace content by regex.
     *
     * @param text the text
     * @param regex the regex
     * @return the string
     */
    private static String regReplace(String text, String regex) {

        String result = text;

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {

            String url = matcher.group();

            LOG.info("matched url path:{}", url);

            String convertUrl = null;
            if (StringUtils.equals(regex, ORIG_URL_REGEX)) {

                convertUrl = getRelativeRewriteUrl(url);
            } else if (StringUtils.equals(regex, REWRITE_URL_REGEX)) {
                //                convertUrl = getRelativeOrigUrl(url);
            }

            if (!StringUtils.equals(convertUrl, url)) {

                result = StringUtils.replace(result, url, convertUrl);
            }
        }

        return result;
    }

    /**
     * Get relative path rewrite url.
     *
     * @param origUrl rewrite before url
     * @return rewrite after url
     */
    public static String getRelativeRewriteUrl(String origUrl) {

        if (StringUtils.isBlank(origUrl)) {
            return null;
        }

        // rewrite after url
        StringBuffer rewriteUrl = new StringBuffer();

        //url action part
        String action = origUrl;

        //url params part
        String params = null;

        if (StringUtils.indexOf(origUrl, '?') != -1) {

            String[] urlFragments = StringUtils.split(origUrl, "?");

            action = urlFragments[0];

            if (urlFragments.length > 1) {
                params = urlFragments[1];
            }
        }

        //get the action urlmapping
        URLMapping urlMapping = getURLMappingByValue(action, URLMappingEnum.ACTION);

        if (urlMapping == null) {
            return origUrl;
//            rewriteUrl.append(action);
//            if (StringUtils.isNotBlank(params)) {
//
//                rewriteUrl.append("?");
//                rewriteUrl.append(getPrefixRewriteParams(params));
//            }
        } else if (StringUtils.equals(urlMapping.getSplitKey()[2], PREFIX_DEFINITION)) {

            rewriteUrl.append("/");
            rewriteUrl.append(urlMapping.getSplitKey()[3]);

            if (StringUtils.isNotBlank(params)) {

                rewriteUrl.append("?");
                rewriteUrl.append(getPrefixRewriteParams(params));
            }
        } else {

            rewriteUrl.append("/");
            rewriteUrl.append(urlMapping.getSplitKey()[2]);
            if (StringUtils.isNotBlank(params)) {

                String result = getRewriteParams(params);

                if (!StringUtils.startsWith(result, "?")) {
                    rewriteUrl.append(PARAM_SEPARATOR);
                }
                rewriteUrl.append(getRewriteParams(params));
            }
        }

        return rewriteUrl.toString();
    }

    /**
     * Get prefix rewrite params.
     *
     * @param params the params
     * @return the string
     */
    private static URLMapping[] getParamsURLMappings(String params) {

        if (StringUtils.isBlank(params)) {
            return null;
        }

        String[] ps = StringUtils.split(params, '&');

        URLMapping[] urlMappings = new URLMapping[ps.length];

        for (int i = 0; i < ps.length; i++) {

            String[] pArr = StringUtils.split(ps[i], '=');

            URLMapping urlMapping = getURLMappingByValue(pArr[0], URLMappingEnum.PARAM);

            if (urlMapping == null) {
                urlMapping = new URLMapping();
                urlMapping.setSplitKey(StringUtils.split("url$param$1000$" + pArr[0], '$'));
            }

            if (pArr.length > 1) {

                urlMapping.setFinalValue(pArr[1]);

            } else {

                urlMapping.setFinalValue(PARAM_NO_VALUE);
            }

            urlMappings[i] = urlMapping;
        }

        for (int i = 0; i < urlMappings.length; i++) {

            for (int j = 0; j < urlMappings.length - i - 1; j++) {

                if (urlMappings[j].getSplitKey() == null) {
                    continue;
                }

                if (Integer.valueOf(urlMappings[j].getSplitKey()[2]) > Integer
                    .valueOf(urlMappings[j + 1].getSplitKey()[2])) {

                    URLMapping temp = urlMappings[j];
                    urlMappings[j] = urlMappings[j + 1];
                    urlMappings[j + 1] = temp;
                }
            }
        }

        return urlMappings;

    }

    /**
     * Gets prefix rewrite params.
     *
     * @param params the params
     * @return the prefix rewrite params
     */
    private static String getRewriteParams(String params) {

        URLMapping[] urlMappings = getParamsURLMappings(params);

        if (urlMappings == null) {
            return params;
        }

        StringBuilder rewriteParams = new StringBuilder();

        StringBuilder noRewriteParams = new StringBuilder();

        for (URLMapping urlMapping : urlMappings) {

            if (urlMapping.getKey() != null) {

                rewriteParams.append(urlMapping.getSplitKey()[3]);
                rewriteParams.append(PARAM_SEPARATOR);
                rewriteParams.append(urlMapping.getFinalValue());
                rewriteParams.append(PARAM_SEPARATOR);
            } else {

                noRewriteParams.append(urlMapping.getSplitKey()[3]);
                noRewriteParams.append("=");
                noRewriteParams.append(urlMapping.getFinalValue());
                noRewriteParams.append("&");

            }
        }

        StringBuilder result = new StringBuilder();

        if (rewriteParams.length() > 0) {

            result.append(rewriteParams.substring(0, rewriteParams.length() - 1));
            result.append(URL_SUFFIX);
        }

        if (noRewriteParams.length() > 0) {

            result.append("?");
            result.append(noRewriteParams.substring(0, noRewriteParams.length() - 1));
        }

        return result.toString();
    }

    /**
     * Gets prefix rewrite params.
     *
     * @param params the params
     * @return the prefix rewrite params
     */
    private static String getPrefixRewriteParams(String params) {

        URLMapping[] urlMappings = getParamsURLMappings(params);

        if (urlMappings == null) {
            return params;
        }

        StringBuilder sb = new StringBuilder();

        for (URLMapping urlMapping : urlMappings) {

            sb.append(urlMapping.getSplitKey()[3]);
            sb.append("=");
            sb.append(urlMapping.getFinalValue());
            sb.append("&");
        }

        return StringUtils.substring(sb.toString(), 0, sb.length() - 1);
    }

    /**
     * Get URLMapping object by key
     *
     * @param key the key
     * @return URL mapping by key
     */
    private static URLMapping getURLMappingByKey(String key, URLMappingEnum urlMappingEnum) {

        if (urlMappingEnum == URLMappingEnum.ACTION) {

            for (URLMapping urlMapping : urlMappingList) {

                if (StringUtils.equals(urlMapping.getSplitKey()[1], ACTION_PREFIX)
                    && StringUtils.equals(urlMapping.getSplitKey()[2], key)) {
                    return urlMapping;
                }
            }
        } else {

            for (URLMapping urlMapping : urlMappingList) {

                if (StringUtils.equals(urlMapping.getSplitKey()[1], PARAM_PREFIX)
                    && StringUtils.equals(urlMapping.getSplitKey()[2], key)) {
                    return urlMapping;
                }
            }
        }

        return null;
    }

    /**
     * Get URLMapping object by value.Is the action for finalPath
     *
     * @param value the value
     * @return URL mapping by value
     */
    private static URLMapping getURLMappingByValue(String value, URLMappingEnum urlMappingEnum) {

        if (urlMappingEnum == URLMappingEnum.ACTION) {

            for (URLMapping urlMapping : urlMappingList) {

                if (StringUtils.equals(urlMapping.getSplitKey()[1], ACTION_PREFIX)
                    && StringUtils.equals(urlMapping.getFinalValue(), value)) {
                    return urlMapping;
                }
            }
        } else {

            for (URLMapping urlMapping : urlMappingList) {

                if (StringUtils.equals(urlMapping.getSplitKey()[1], PARAM_PREFIX)
                    && StringUtils.equals(urlMapping.getValue(), value)) {
                    return urlMapping;
                }
            }
        }
        return null;
    }

}
