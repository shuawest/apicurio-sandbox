// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: samplea.proto

package com.agregsandbox.modela;

public interface FactWrapper2OrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.agregsandbox.modela.FactWrapper2)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string topic = 1;</code>
   * @return The topic.
   */
  java.lang.String getTopic();
  /**
   * <code>string topic = 1;</code>
   * @return The bytes for topic.
   */
  com.google.protobuf.ByteString
      getTopicBytes();

  /**
   * <code>string prefix = 2;</code>
   * @return The prefix.
   */
  java.lang.String getPrefix();
  /**
   * <code>string prefix = 2;</code>
   * @return The bytes for prefix.
   */
  com.google.protobuf.ByteString
      getPrefixBytes();

  /**
   * <code>string key = 3;</code>
   * @return The key.
   */
  java.lang.String getKey();
  /**
   * <code>string key = 3;</code>
   * @return The bytes for key.
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>.com.agregsandbox.modela.FactA factA = 20;</code>
   * @return Whether the factA field is set.
   */
  boolean hasFactA();
  /**
   * <code>.com.agregsandbox.modela.FactA factA = 20;</code>
   * @return The factA.
   */
  com.agregsandbox.modela.FactA getFactA();
  /**
   * <code>.com.agregsandbox.modela.FactA factA = 20;</code>
   */
  com.agregsandbox.modela.FactAOrBuilder getFactAOrBuilder();

  /**
   * <code>.com.agregsandbox.modela.FactB factB = 21;</code>
   * @return Whether the factB field is set.
   */
  boolean hasFactB();
  /**
   * <code>.com.agregsandbox.modela.FactB factB = 21;</code>
   * @return The factB.
   */
  com.agregsandbox.modela.FactB getFactB();
  /**
   * <code>.com.agregsandbox.modela.FactB factB = 21;</code>
   */
  com.agregsandbox.modela.FactBOrBuilder getFactBOrBuilder();
}
