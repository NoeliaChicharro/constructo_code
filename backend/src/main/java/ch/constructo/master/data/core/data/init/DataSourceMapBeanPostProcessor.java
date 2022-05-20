package ch.constructo.master.data.core.data.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DataSourceMapBeanPostProcessor implements BeanPostProcessor {

  private final transient Logger log = LoggerFactory.getLogger(DataSourceMapBeanPostProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(Object o, String s) throws BeansException{
    return o;
  }

  @Override
  public Object postProcessAfterInitialization(Object o, String s) throws BeansException{
    log.info("Post initialization of bean " + s);
    return o;
  }
}
