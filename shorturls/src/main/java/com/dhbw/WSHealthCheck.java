package com.dhbw;


import com.codahale.metrics.health.HealthCheck;

public class WSHealthCheck extends HealthCheck
{
  public WSHealthCheck()
  {
  }

  @Override
  protected Result check() throws Exception
  {
    return Result.healthy();
  }
}