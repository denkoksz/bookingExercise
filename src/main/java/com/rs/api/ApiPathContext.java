package com.rs.api;


import static com.rs.api.ContextConstants.DEFAULT_LIMIT;
import static com.rs.api.ContextConstants.DEFAULT_OFFSET;
import static com.rs.api.ContextConstants.LIMIT;
import static com.rs.api.ContextConstants.OFFSET;

public class ApiPathContext extends BasePathContext<ApiPathContext> {

  public ApiPathContext setLimit(final String limit) {
    super.addParam(LIMIT, limit);
    return this;
  }

  public Integer getLimit() {
    return hasParam(LIMIT) ? Integer.parseInt(getParam(LIMIT)) : DEFAULT_LIMIT;
  }

  public ApiPathContext setOffset(final String offset) {
    super.addParam(OFFSET, offset);
    return this;
  }

  public Integer getOffset() {
    return hasParam(OFFSET) ? Integer.parseInt(getParam(OFFSET)) : DEFAULT_OFFSET;
  }
}

