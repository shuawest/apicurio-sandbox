// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: samplea.proto

package com.agregsandbox.modela;

public final class Samplea {
  private Samplea() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_GatewayErrorA_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_GatewayErrorA_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_MyMessageA_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_MyMessageA_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_MyMessageB_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_MyMessageB_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_OneOfFactWrapper_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_OneOfFactWrapper_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_FactWrapper2_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_FactWrapper2_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_FactA_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_FactA_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_agregsandbox_modela_FactB_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_agregsandbox_modela_FactB_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rsamplea.proto\022\027com.agregsandbox.modela" +
      "\032\037google/protobuf/timestamp.proto\"\225\001\n\rGa" +
      "tewayErrorA\022\r\n\005route\030\001 \001(\t\022\017\n\007headers\030\002 " +
      "\001(\t\022\014\n\004body\030\003 \001(\t\022\021\n\texMessage\030\004 \001(\t\022\024\n\014" +
      "exStackTrace\030\005 \001(\t\022-\n\ttimestamp\030\006 \001(\0132\032." +
      "google.protobuf.Timestamp\"U\n\nMyMessageA\022" +
      "\n\n\002id\030\001 \001(\005\022\014\n\004name\030\002 \001(\t\022-\n\ttimestamp\030\003" +
      " \001(\0132\032.google.protobuf.Timestamp\"U\n\nMyMe" +
      "ssageB\022\n\n\002id\030\001 \001(\005\022\014\n\004name\030\002 \001(\t\022-\n\ttime" +
      "stamp\030\003 \001(\0132\032.google.protobuf.Timestamp\"" +
      "\234\001\n\020OneOfFactWrapper\022\r\n\005topic\030\001 \001(\t\022\016\n\006p" +
      "refix\030\002 \001(\t\022\013\n\003key\030\003 \001(\t\022-\n\005factA\030\024 \001(\0132" +
      "\036.com.agregsandbox.modela.FactA\022-\n\005factB" +
      "\030\025 \001(\0132\036.com.agregsandbox.modela.FactB\"\230" +
      "\001\n\014FactWrapper2\022\r\n\005topic\030\001 \001(\t\022\016\n\006prefix" +
      "\030\002 \001(\t\022\013\n\003key\030\003 \001(\t\022-\n\005factA\030\024 \001(\0132\036.com" +
      ".agregsandbox.modela.FactA\022-\n\005factB\030\025 \001(" +
      "\0132\036.com.agregsandbox.modela.FactB\"\026\n\005Fac" +
      "tA\022\r\n\005nameA\030\001 \001(\t\"\026\n\005FactB\022\r\n\005nameB\030\001 \001(" +
      "\tB\033\n\027com.agregsandbox.modelaP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.TimestampProto.getDescriptor(),
        });
    internal_static_com_agregsandbox_modela_GatewayErrorA_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_agregsandbox_modela_GatewayErrorA_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_GatewayErrorA_descriptor,
        new java.lang.String[] { "Route", "Headers", "Body", "ExMessage", "ExStackTrace", "Timestamp", });
    internal_static_com_agregsandbox_modela_MyMessageA_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_agregsandbox_modela_MyMessageA_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_MyMessageA_descriptor,
        new java.lang.String[] { "Id", "Name", "Timestamp", });
    internal_static_com_agregsandbox_modela_MyMessageB_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_agregsandbox_modela_MyMessageB_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_MyMessageB_descriptor,
        new java.lang.String[] { "Id", "Name", "Timestamp", });
    internal_static_com_agregsandbox_modela_OneOfFactWrapper_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_agregsandbox_modela_OneOfFactWrapper_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_OneOfFactWrapper_descriptor,
        new java.lang.String[] { "Topic", "Prefix", "Key", "FactA", "FactB", });
    internal_static_com_agregsandbox_modela_FactWrapper2_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_agregsandbox_modela_FactWrapper2_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_FactWrapper2_descriptor,
        new java.lang.String[] { "Topic", "Prefix", "Key", "FactA", "FactB", });
    internal_static_com_agregsandbox_modela_FactA_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_agregsandbox_modela_FactA_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_FactA_descriptor,
        new java.lang.String[] { "NameA", });
    internal_static_com_agregsandbox_modela_FactB_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_com_agregsandbox_modela_FactB_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_agregsandbox_modela_FactB_descriptor,
        new java.lang.String[] { "NameB", });
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
