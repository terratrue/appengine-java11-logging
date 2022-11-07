// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/appengine/logging/v1/request_log.proto

package com.google.appengine.logging.v1;

/**
 *
 *
 * <pre>
 * A reference to a particular snapshot of the source tree used to build and
 * deploy an application.
 * </pre>
 *
 * Protobuf type {@code google.appengine.logging.v1.SourceReference}
 */
@SuppressWarnings("ReferenceEquality")
public final class SourceReference extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.appengine.logging.v1.SourceReference)
    SourceReferenceOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use SourceReference.newBuilder() to construct.
  private SourceReference(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private SourceReference() {
    repository_ = "";
    revisionId_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(UnusedPrivateParameter unused) {
    return new SourceReference();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private SourceReference(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10:
            {
              String s = input.readStringRequireUtf8();

              repository_ = s;
              break;
            }
          case 18:
            {
              String s = input.readStringRequireUtf8();

              revisionId_ = s;
              break;
            }
          default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return RequestLogProto.internal_static_google_appengine_logging_v1_SourceReference_descriptor;
  }

  @Override
  protected FieldAccessorTable internalGetFieldAccessorTable() {
    return RequestLogProto
        .internal_static_google_appengine_logging_v1_SourceReference_fieldAccessorTable
        .ensureFieldAccessorsInitialized(SourceReference.class, Builder.class);
  }

  public static final int REPOSITORY_FIELD_NUMBER = 1;
  private volatile Object repository_;
  /**
   *
   *
   * <pre>
   * Optional. A URI string identifying the repository.
   * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
   * </pre>
   *
   * <code>string repository = 1;</code>
   *
   * @return The repository.
   */
  @Override
  public String getRepository() {
    Object ref = repository_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      repository_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * Optional. A URI string identifying the repository.
   * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
   * </pre>
   *
   * <code>string repository = 1;</code>
   *
   * @return The bytes for repository.
   */
  @Override
  public com.google.protobuf.ByteString getRepositoryBytes() {
    Object ref = repository_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((String) ref);
      repository_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int REVISION_ID_FIELD_NUMBER = 2;
  private volatile Object revisionId_;
  /**
   *
   *
   * <pre>
   * The canonical and persistent identifier of the deployed revision.
   * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
   * </pre>
   *
   * <code>string revision_id = 2;</code>
   *
   * @return The revisionId.
   */
  @Override
  public String getRevisionId() {
    Object ref = revisionId_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      revisionId_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * The canonical and persistent identifier of the deployed revision.
   * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
   * </pre>
   *
   * <code>string revision_id = 2;</code>
   *
   * @return The bytes for revisionId.
   */
  @Override
  public com.google.protobuf.ByteString getRevisionIdBytes() {
    Object ref = revisionId_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((String) ref);
      revisionId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;

  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(repository_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, repository_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(revisionId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, revisionId_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(repository_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, repository_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(revisionId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, revisionId_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof SourceReference)) {
      return super.equals(obj);
    }
    SourceReference other = (SourceReference) obj;

    if (!getRepository().equals(other.getRepository())) return false;
    if (!getRevisionId().equals(other.getRevisionId())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + REPOSITORY_FIELD_NUMBER;
    hash = (53 * hash) + getRepository().hashCode();
    hash = (37 * hash) + REVISION_ID_FIELD_NUMBER;
    hash = (53 * hash) + getRevisionId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static SourceReference parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static SourceReference parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static SourceReference parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static SourceReference parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static SourceReference parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static SourceReference parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static SourceReference parseFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static SourceReference parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static SourceReference parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static SourceReference parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static SourceReference parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static SourceReference parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(SourceReference prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   *
   *
   * <pre>
   * A reference to a particular snapshot of the source tree used to build and
   * deploy an application.
   * </pre>
   *
   * Protobuf type {@code google.appengine.logging.v1.SourceReference}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.appengine.logging.v1.SourceReference)
      SourceReferenceOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return RequestLogProto.internal_static_google_appengine_logging_v1_SourceReference_descriptor;
    }

    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
      return RequestLogProto
          .internal_static_google_appengine_logging_v1_SourceReference_fieldAccessorTable
          .ensureFieldAccessorsInitialized(SourceReference.class, Builder.class);
    }

    // Construct using com.google.appengine.logging.v1.SourceReference.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {}
    }

    @Override
    public Builder clear() {
      super.clear();
      repository_ = "";

      revisionId_ = "";

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return RequestLogProto.internal_static_google_appengine_logging_v1_SourceReference_descriptor;
    }

    @Override
    public SourceReference getDefaultInstanceForType() {
      return SourceReference.getDefaultInstance();
    }

    @Override
    public SourceReference build() {
      SourceReference result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public SourceReference buildPartial() {
      SourceReference result = new SourceReference(this);
      result.repository_ = repository_;
      result.revisionId_ = revisionId_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }

    @Override
    public Builder setField(com.google.protobuf.Descriptors.FieldDescriptor field, Object value) {
      return super.setField(field, value);
    }

    @Override
    public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @Override
    public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, Object value) {
      return super.addRepeatedField(field, value);
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof SourceReference) {
        return mergeFrom((SourceReference) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(SourceReference other) {
      if (other == SourceReference.getDefaultInstance()) return this;
      if (!other.getRepository().isEmpty()) {
        repository_ = other.repository_;
        onChanged();
      }
      if (!other.getRevisionId().isEmpty()) {
        revisionId_ = other.revisionId_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      SourceReference parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (SourceReference) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private Object repository_ = "";
    /**
     *
     *
     * <pre>
     * Optional. A URI string identifying the repository.
     * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
     * </pre>
     *
     * <code>string repository = 1;</code>
     *
     * @return The repository.
     */
    public String getRepository() {
      Object ref = repository_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        repository_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * Optional. A URI string identifying the repository.
     * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
     * </pre>
     *
     * <code>string repository = 1;</code>
     *
     * @return The bytes for repository.
     */
    public com.google.protobuf.ByteString getRepositoryBytes() {
      Object ref = repository_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        repository_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * Optional. A URI string identifying the repository.
     * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
     * </pre>
     *
     * <code>string repository = 1;</code>
     *
     * @param value The repository to set.
     * @return This builder for chaining.
     */
    public Builder setRepository(String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      repository_ = value;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. A URI string identifying the repository.
     * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
     * </pre>
     *
     * <code>string repository = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearRepository() {

      repository_ = getDefaultInstance().getRepository();
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. A URI string identifying the repository.
     * Example: "https://github.com/GoogleCloudPlatform/kubernetes.git"
     * </pre>
     *
     * <code>string repository = 1;</code>
     *
     * @param value The bytes for repository to set.
     * @return This builder for chaining.
     */
    public Builder setRepositoryBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      repository_ = value;
      onChanged();
      return this;
    }

    private Object revisionId_ = "";
    /**
     *
     *
     * <pre>
     * The canonical and persistent identifier of the deployed revision.
     * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
     * </pre>
     *
     * <code>string revision_id = 2;</code>
     *
     * @return The revisionId.
     */
    public String getRevisionId() {
      Object ref = revisionId_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        revisionId_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The canonical and persistent identifier of the deployed revision.
     * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
     * </pre>
     *
     * <code>string revision_id = 2;</code>
     *
     * @return The bytes for revisionId.
     */
    public com.google.protobuf.ByteString getRevisionIdBytes() {
      Object ref = revisionId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        revisionId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The canonical and persistent identifier of the deployed revision.
     * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
     * </pre>
     *
     * <code>string revision_id = 2;</code>
     *
     * @param value The revisionId to set.
     * @return This builder for chaining.
     */
    public Builder setRevisionId(String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      revisionId_ = value;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The canonical and persistent identifier of the deployed revision.
     * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
     * </pre>
     *
     * <code>string revision_id = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearRevisionId() {

      revisionId_ = getDefaultInstance().getRevisionId();
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The canonical and persistent identifier of the deployed revision.
     * Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b"
     * </pre>
     *
     * <code>string revision_id = 2;</code>
     *
     * @param value The bytes for revisionId to set.
     * @return This builder for chaining.
     */
    public Builder setRevisionIdBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      revisionId_ = value;
      onChanged();
      return this;
    }

    @Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:google.appengine.logging.v1.SourceReference)
  }

  // @@protoc_insertion_point(class_scope:google.appengine.logging.v1.SourceReference)
  private static final SourceReference DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new SourceReference();
  }

  public static SourceReference getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SourceReference> PARSER =
      new com.google.protobuf.AbstractParser<SourceReference>() {
        @Override
        public SourceReference parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new SourceReference(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<SourceReference> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<SourceReference> getParserForType() {
    return PARSER;
  }

  @Override
  public SourceReference getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}