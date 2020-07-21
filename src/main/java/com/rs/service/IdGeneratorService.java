package com.rs.service;

public interface IdGeneratorService {

  /**
   * Generates a new id using the red bull id generator.
   **/
  String getId(String prefix);
}
