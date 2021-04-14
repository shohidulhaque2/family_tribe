package com.shohidulhaque.user_registry_service.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExpirationTimeCalculator {

  Clock clock = Clock.systemUTC();

  public Instant addToCurrentUTCTime(long numberOfMinutes) {
    if(numberOfMinutes < 0){
      Instant.now(this.clock);
    }
    return Instant.now(this.clock).plus(Duration.ofMinutes(numberOfMinutes));
  }


  public boolean hasTimeExpired(Instant expirationTime) {
    return Instant.now().isAfter(expirationTime);
  }

}
