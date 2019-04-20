package com.spring.cloud.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * error filter
 * filterType :
 *   {
 *      "pre" for pre-routing filtering,
 *      "route" for routing to an origin,
 *      "post" for post-routing filters,
 *      "error" for error handling.
 *      "static" type for static responses see  StaticResponseFilter.
 *   }
 * filterOrder  : the int order of a filter
 * shouldFilter : true if the run() method should be invoked. false will not invoke the run() method
 * run          : if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
 */
public class ErrorFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        logger.error("zuul error filter. exception={}", throwable);
        return null;
    }
}
