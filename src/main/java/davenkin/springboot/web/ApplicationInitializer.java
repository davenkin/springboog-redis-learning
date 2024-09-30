package davenkin.springboot.web;

import davenkin.springboot.web.cache.CacheClearer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final CacheClearer cacheClearer;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    //clear all cache at startup
    this.cacheClearer.evictAllCache();
  }
}

