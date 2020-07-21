package com.rs.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessageConstants;

import static com.rs.api.ContextConstants.APP;
import static com.rs.message.ErrorMessages.PATH_VARIABLE_NOT_SET;

public abstract class BasePathContext<T extends BasePathContext<T>> {

  private HashMap<String, String> params = new HashMap<>();

  public T setApp(final String app) {
    params.put(APP, app);
    return (T) this;
  }

  public Map<String, String> getParams() {
    return params;
  }

  protected String getParam(final String key) {
    return Optional.ofNullable(params.get(key))
      .or(() -> {
        throw new ServiceException(PATH_VARIABLE_NOT_SET, Optional.of(Map.of(ErrorMessageConstants.PATH_VARIABLE_NAME, key)));
      })
      .get();
  }

  protected boolean hasParam(final String key) {
    return params.containsKey(key);
  }

  public String getApp() {
    return getParam(APP);
  }

  protected void addParam(final String key, final String variable) {
    params.put(key, variable);
  }
}
