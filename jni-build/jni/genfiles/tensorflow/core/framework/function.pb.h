// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tensorflow/core/framework/function.proto

#ifndef PROTOBUF_tensorflow_2fcore_2fframework_2ffunction_2eproto__INCLUDED
#define PROTOBUF_tensorflow_2fcore_2fframework_2ffunction_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3000000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3000000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/map.h>
#include <google/protobuf/map_field_inl.h>
#include <google/protobuf/unknown_field_set.h>
#include "tensorflow/core/framework/attr_value.pb.h"
#include "tensorflow/core/framework/op_def.pb.h"
// @@protoc_insertion_point(includes)

namespace tensorflow {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
void protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
void protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ffunction_2eproto();

class FunctionDef;
class FunctionDefLibrary;
class FunctionDef_Node;

// ===================================================================

class FunctionDefLibrary : public ::google::protobuf::Message {
 public:
  FunctionDefLibrary();
  virtual ~FunctionDefLibrary();

  FunctionDefLibrary(const FunctionDefLibrary& from);

  inline FunctionDefLibrary& operator=(const FunctionDefLibrary& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const FunctionDefLibrary& default_instance();

  void Swap(FunctionDefLibrary* other);

  // implements Message ----------------------------------------------

  inline FunctionDefLibrary* New() const { return New(NULL); }

  FunctionDefLibrary* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const FunctionDefLibrary& from);
  void MergeFrom(const FunctionDefLibrary& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(FunctionDefLibrary* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // repeated .tensorflow.FunctionDef function = 1;
  int function_size() const;
  void clear_function();
  static const int kFunctionFieldNumber = 1;
  const ::tensorflow::FunctionDef& function(int index) const;
  ::tensorflow::FunctionDef* mutable_function(int index);
  ::tensorflow::FunctionDef* add_function();
  ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef >*
      mutable_function();
  const ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef >&
      function() const;

  // @@protoc_insertion_point(class_scope:tensorflow.FunctionDefLibrary)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef > function_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ffunction_2eproto();

  void InitAsDefaultInstance();
  static FunctionDefLibrary* default_instance_;
};
// -------------------------------------------------------------------

class FunctionDef_Node : public ::google::protobuf::Message {
 public:
  FunctionDef_Node();
  virtual ~FunctionDef_Node();

  FunctionDef_Node(const FunctionDef_Node& from);

  inline FunctionDef_Node& operator=(const FunctionDef_Node& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const FunctionDef_Node& default_instance();

  void Swap(FunctionDef_Node* other);

  // implements Message ----------------------------------------------

  inline FunctionDef_Node* New() const { return New(NULL); }

  FunctionDef_Node* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const FunctionDef_Node& from);
  void MergeFrom(const FunctionDef_Node& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(FunctionDef_Node* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------


  // accessors -------------------------------------------------------

  // repeated string ret = 1;
  int ret_size() const;
  void clear_ret();
  static const int kRetFieldNumber = 1;
  const ::std::string& ret(int index) const;
  ::std::string* mutable_ret(int index);
  void set_ret(int index, const ::std::string& value);
  void set_ret(int index, const char* value);
  void set_ret(int index, const char* value, size_t size);
  ::std::string* add_ret();
  void add_ret(const ::std::string& value);
  void add_ret(const char* value);
  void add_ret(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& ret() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_ret();

  // optional string op = 2;
  void clear_op();
  static const int kOpFieldNumber = 2;
  const ::std::string& op() const;
  void set_op(const ::std::string& value);
  void set_op(const char* value);
  void set_op(const char* value, size_t size);
  ::std::string* mutable_op();
  ::std::string* release_op();
  void set_allocated_op(::std::string* op);

  // repeated string arg = 3;
  int arg_size() const;
  void clear_arg();
  static const int kArgFieldNumber = 3;
  const ::std::string& arg(int index) const;
  ::std::string* mutable_arg(int index);
  void set_arg(int index, const ::std::string& value);
  void set_arg(int index, const char* value);
  void set_arg(int index, const char* value, size_t size);
  ::std::string* add_arg();
  void add_arg(const ::std::string& value);
  void add_arg(const char* value);
  void add_arg(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& arg() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_arg();

  // repeated string dep = 4;
  int dep_size() const;
  void clear_dep();
  static const int kDepFieldNumber = 4;
  const ::std::string& dep(int index) const;
  ::std::string* mutable_dep(int index);
  void set_dep(int index, const ::std::string& value);
  void set_dep(int index, const char* value);
  void set_dep(int index, const char* value, size_t size);
  ::std::string* add_dep();
  void add_dep(const ::std::string& value);
  void add_dep(const char* value);
  void add_dep(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& dep() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_dep();

  // map<string, .tensorflow.AttrValue> attr = 5;
  int attr_size() const;
  void clear_attr();
  static const int kAttrFieldNumber = 5;
  const ::google::protobuf::Map< ::std::string, ::tensorflow::AttrValue >&
      attr() const;
  ::google::protobuf::Map< ::std::string, ::tensorflow::AttrValue >*
      mutable_attr();

  // @@protoc_insertion_point(class_scope:tensorflow.FunctionDef.Node)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::google::protobuf::RepeatedPtrField< ::std::string> ret_;
  ::google::protobuf::internal::ArenaStringPtr op_;
  ::google::protobuf::RepeatedPtrField< ::std::string> arg_;
  ::google::protobuf::RepeatedPtrField< ::std::string> dep_;
  typedef ::google::protobuf::internal::MapEntryLite<
      ::std::string, ::tensorflow::AttrValue,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      ::google::protobuf::internal::WireFormatLite::TYPE_MESSAGE,
      0 >
      FunctionDef_Node_AttrEntry;
  ::google::protobuf::internal::MapField<
      ::std::string, ::tensorflow::AttrValue,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      ::google::protobuf::internal::WireFormatLite::TYPE_MESSAGE,
      0 > attr_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ffunction_2eproto();

  void InitAsDefaultInstance();
  static FunctionDef_Node* default_instance_;
};
// -------------------------------------------------------------------

class FunctionDef : public ::google::protobuf::Message {
 public:
  FunctionDef();
  virtual ~FunctionDef();

  FunctionDef(const FunctionDef& from);

  inline FunctionDef& operator=(const FunctionDef& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const FunctionDef& default_instance();

  void Swap(FunctionDef* other);

  // implements Message ----------------------------------------------

  inline FunctionDef* New() const { return New(NULL); }

  FunctionDef* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const FunctionDef& from);
  void MergeFrom(const FunctionDef& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(FunctionDef* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  typedef FunctionDef_Node Node;

  // accessors -------------------------------------------------------

  // optional .tensorflow.OpDef signature = 1;
  bool has_signature() const;
  void clear_signature();
  static const int kSignatureFieldNumber = 1;
  const ::tensorflow::OpDef& signature() const;
  ::tensorflow::OpDef* mutable_signature();
  ::tensorflow::OpDef* release_signature();
  void set_allocated_signature(::tensorflow::OpDef* signature);

  // repeated .tensorflow.FunctionDef.Node node = 2;
  int node_size() const;
  void clear_node();
  static const int kNodeFieldNumber = 2;
  const ::tensorflow::FunctionDef_Node& node(int index) const;
  ::tensorflow::FunctionDef_Node* mutable_node(int index);
  ::tensorflow::FunctionDef_Node* add_node();
  ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef_Node >*
      mutable_node();
  const ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef_Node >&
      node() const;

  // @@protoc_insertion_point(class_scope:tensorflow.FunctionDef)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::tensorflow::OpDef* signature_;
  ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef_Node > node_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ffunction_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ffunction_2eproto();

  void InitAsDefaultInstance();
  static FunctionDef* default_instance_;
};
// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// FunctionDefLibrary

// repeated .tensorflow.FunctionDef function = 1;
inline int FunctionDefLibrary::function_size() const {
  return function_.size();
}
inline void FunctionDefLibrary::clear_function() {
  function_.Clear();
}
inline const ::tensorflow::FunctionDef& FunctionDefLibrary::function(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDefLibrary.function)
  return function_.Get(index);
}
inline ::tensorflow::FunctionDef* FunctionDefLibrary::mutable_function(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDefLibrary.function)
  return function_.Mutable(index);
}
inline ::tensorflow::FunctionDef* FunctionDefLibrary::add_function() {
  // @@protoc_insertion_point(field_add:tensorflow.FunctionDefLibrary.function)
  return function_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef >*
FunctionDefLibrary::mutable_function() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.FunctionDefLibrary.function)
  return &function_;
}
inline const ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef >&
FunctionDefLibrary::function() const {
  // @@protoc_insertion_point(field_list:tensorflow.FunctionDefLibrary.function)
  return function_;
}

// -------------------------------------------------------------------

// FunctionDef_Node

// repeated string ret = 1;
inline int FunctionDef_Node::ret_size() const {
  return ret_.size();
}
inline void FunctionDef_Node::clear_ret() {
  ret_.Clear();
}
inline const ::std::string& FunctionDef_Node::ret(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.Node.ret)
  return ret_.Get(index);
}
inline ::std::string* FunctionDef_Node::mutable_ret(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.Node.ret)
  return ret_.Mutable(index);
}
inline void FunctionDef_Node::set_ret(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:tensorflow.FunctionDef.Node.ret)
  ret_.Mutable(index)->assign(value);
}
inline void FunctionDef_Node::set_ret(int index, const char* value) {
  ret_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:tensorflow.FunctionDef.Node.ret)
}
inline void FunctionDef_Node::set_ret(int index, const char* value, size_t size) {
  ret_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:tensorflow.FunctionDef.Node.ret)
}
inline ::std::string* FunctionDef_Node::add_ret() {
  return ret_.Add();
}
inline void FunctionDef_Node::add_ret(const ::std::string& value) {
  ret_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:tensorflow.FunctionDef.Node.ret)
}
inline void FunctionDef_Node::add_ret(const char* value) {
  ret_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:tensorflow.FunctionDef.Node.ret)
}
inline void FunctionDef_Node::add_ret(const char* value, size_t size) {
  ret_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:tensorflow.FunctionDef.Node.ret)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
FunctionDef_Node::ret() const {
  // @@protoc_insertion_point(field_list:tensorflow.FunctionDef.Node.ret)
  return ret_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
FunctionDef_Node::mutable_ret() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.FunctionDef.Node.ret)
  return &ret_;
}

// optional string op = 2;
inline void FunctionDef_Node::clear_op() {
  op_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& FunctionDef_Node::op() const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.Node.op)
  return op_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void FunctionDef_Node::set_op(const ::std::string& value) {
  
  op_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:tensorflow.FunctionDef.Node.op)
}
inline void FunctionDef_Node::set_op(const char* value) {
  
  op_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:tensorflow.FunctionDef.Node.op)
}
inline void FunctionDef_Node::set_op(const char* value, size_t size) {
  
  op_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:tensorflow.FunctionDef.Node.op)
}
inline ::std::string* FunctionDef_Node::mutable_op() {
  
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.Node.op)
  return op_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* FunctionDef_Node::release_op() {
  
  return op_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void FunctionDef_Node::set_allocated_op(::std::string* op) {
  if (op != NULL) {
    
  } else {
    
  }
  op_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), op);
  // @@protoc_insertion_point(field_set_allocated:tensorflow.FunctionDef.Node.op)
}

// repeated string arg = 3;
inline int FunctionDef_Node::arg_size() const {
  return arg_.size();
}
inline void FunctionDef_Node::clear_arg() {
  arg_.Clear();
}
inline const ::std::string& FunctionDef_Node::arg(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.Node.arg)
  return arg_.Get(index);
}
inline ::std::string* FunctionDef_Node::mutable_arg(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.Node.arg)
  return arg_.Mutable(index);
}
inline void FunctionDef_Node::set_arg(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:tensorflow.FunctionDef.Node.arg)
  arg_.Mutable(index)->assign(value);
}
inline void FunctionDef_Node::set_arg(int index, const char* value) {
  arg_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:tensorflow.FunctionDef.Node.arg)
}
inline void FunctionDef_Node::set_arg(int index, const char* value, size_t size) {
  arg_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:tensorflow.FunctionDef.Node.arg)
}
inline ::std::string* FunctionDef_Node::add_arg() {
  return arg_.Add();
}
inline void FunctionDef_Node::add_arg(const ::std::string& value) {
  arg_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:tensorflow.FunctionDef.Node.arg)
}
inline void FunctionDef_Node::add_arg(const char* value) {
  arg_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:tensorflow.FunctionDef.Node.arg)
}
inline void FunctionDef_Node::add_arg(const char* value, size_t size) {
  arg_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:tensorflow.FunctionDef.Node.arg)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
FunctionDef_Node::arg() const {
  // @@protoc_insertion_point(field_list:tensorflow.FunctionDef.Node.arg)
  return arg_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
FunctionDef_Node::mutable_arg() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.FunctionDef.Node.arg)
  return &arg_;
}

// repeated string dep = 4;
inline int FunctionDef_Node::dep_size() const {
  return dep_.size();
}
inline void FunctionDef_Node::clear_dep() {
  dep_.Clear();
}
inline const ::std::string& FunctionDef_Node::dep(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.Node.dep)
  return dep_.Get(index);
}
inline ::std::string* FunctionDef_Node::mutable_dep(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.Node.dep)
  return dep_.Mutable(index);
}
inline void FunctionDef_Node::set_dep(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:tensorflow.FunctionDef.Node.dep)
  dep_.Mutable(index)->assign(value);
}
inline void FunctionDef_Node::set_dep(int index, const char* value) {
  dep_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:tensorflow.FunctionDef.Node.dep)
}
inline void FunctionDef_Node::set_dep(int index, const char* value, size_t size) {
  dep_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:tensorflow.FunctionDef.Node.dep)
}
inline ::std::string* FunctionDef_Node::add_dep() {
  return dep_.Add();
}
inline void FunctionDef_Node::add_dep(const ::std::string& value) {
  dep_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:tensorflow.FunctionDef.Node.dep)
}
inline void FunctionDef_Node::add_dep(const char* value) {
  dep_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:tensorflow.FunctionDef.Node.dep)
}
inline void FunctionDef_Node::add_dep(const char* value, size_t size) {
  dep_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:tensorflow.FunctionDef.Node.dep)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
FunctionDef_Node::dep() const {
  // @@protoc_insertion_point(field_list:tensorflow.FunctionDef.Node.dep)
  return dep_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
FunctionDef_Node::mutable_dep() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.FunctionDef.Node.dep)
  return &dep_;
}

// map<string, .tensorflow.AttrValue> attr = 5;
inline int FunctionDef_Node::attr_size() const {
  return attr_.size();
}
inline void FunctionDef_Node::clear_attr() {
  attr_.Clear();
}
inline const ::google::protobuf::Map< ::std::string, ::tensorflow::AttrValue >&
FunctionDef_Node::attr() const {
  // @@protoc_insertion_point(field_map:tensorflow.FunctionDef.Node.attr)
  return attr_.GetMap();
}
inline ::google::protobuf::Map< ::std::string, ::tensorflow::AttrValue >*
FunctionDef_Node::mutable_attr() {
  // @@protoc_insertion_point(field_mutable_map:tensorflow.FunctionDef.Node.attr)
  return attr_.MutableMap();
}

// -------------------------------------------------------------------

// FunctionDef

// optional .tensorflow.OpDef signature = 1;
inline bool FunctionDef::has_signature() const {
  return !_is_default_instance_ && signature_ != NULL;
}
inline void FunctionDef::clear_signature() {
  if (GetArenaNoVirtual() == NULL && signature_ != NULL) delete signature_;
  signature_ = NULL;
}
inline const ::tensorflow::OpDef& FunctionDef::signature() const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.signature)
  return signature_ != NULL ? *signature_ : *default_instance_->signature_;
}
inline ::tensorflow::OpDef* FunctionDef::mutable_signature() {
  
  if (signature_ == NULL) {
    signature_ = new ::tensorflow::OpDef;
  }
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.signature)
  return signature_;
}
inline ::tensorflow::OpDef* FunctionDef::release_signature() {
  
  ::tensorflow::OpDef* temp = signature_;
  signature_ = NULL;
  return temp;
}
inline void FunctionDef::set_allocated_signature(::tensorflow::OpDef* signature) {
  delete signature_;
  signature_ = signature;
  if (signature) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:tensorflow.FunctionDef.signature)
}

// repeated .tensorflow.FunctionDef.Node node = 2;
inline int FunctionDef::node_size() const {
  return node_.size();
}
inline void FunctionDef::clear_node() {
  node_.Clear();
}
inline const ::tensorflow::FunctionDef_Node& FunctionDef::node(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.FunctionDef.node)
  return node_.Get(index);
}
inline ::tensorflow::FunctionDef_Node* FunctionDef::mutable_node(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.FunctionDef.node)
  return node_.Mutable(index);
}
inline ::tensorflow::FunctionDef_Node* FunctionDef::add_node() {
  // @@protoc_insertion_point(field_add:tensorflow.FunctionDef.node)
  return node_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef_Node >*
FunctionDef::mutable_node() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.FunctionDef.node)
  return &node_;
}
inline const ::google::protobuf::RepeatedPtrField< ::tensorflow::FunctionDef_Node >&
FunctionDef::node() const {
  // @@protoc_insertion_point(field_list:tensorflow.FunctionDef.node)
  return node_;
}

#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------

// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace tensorflow

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_tensorflow_2fcore_2fframework_2ffunction_2eproto__INCLUDED