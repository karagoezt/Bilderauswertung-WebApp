package com.dhbw;

import com.dhbw.ws.JSONWs;
import com.dhbw.ws.PlainWs;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WebService extends Application<WebServiceConfiguration>
{
  public static void main(String[] args) throws Exception
  {
    new WebService().run(args);
  }

  @Override
  public String getName()
  {
    return "webService";
  }

  @Override
  public void initialize(Bootstrap<WebServiceConfiguration> bootstrap)
  {
    bootstrap.addBundle(new AssetsBundle("/htmldocs/", "/", "index.html", "static"));
  }

  @Override
  public void run(
    WebServiceConfiguration config,
    Environment environment) throws Exception
  {
    environment.jersey().register(new JSONWs());
    environment.jersey().register(new PlainWs());

    final WSHealthCheck healthCheck = new WSHealthCheck();
    environment.healthChecks().register("template", healthCheck);
  }
}
