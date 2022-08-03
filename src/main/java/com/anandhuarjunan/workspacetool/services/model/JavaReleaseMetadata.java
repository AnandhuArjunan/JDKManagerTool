package com.anandhuarjunan.workspacetool.services.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"architecture",
"features",
"file_type",
"filename",
"image_type",
"java_version",
"jvm_impl",
"md5",
"md5_file",
"os",
"release_type",
"sha1",
"sha1_file",
"sha256",
"sha256_file",
"sha512",
"sha512_file",
"size",
"url",
"vendor",
"version"
})
@Generated("jsonschema2pojo")
public class JavaReleaseMetadata {

@JsonProperty("architecture")
private String architecture;
@JsonProperty("features")
private List<Object> features = null;
@JsonProperty("file_type")
private String fileType;
@JsonProperty("filename")
private String filename;
@JsonProperty("image_type")
private String imageType;
@JsonProperty("java_version")
private String javaVersion;
@JsonProperty("jvm_impl")
private String jvmImpl;
@JsonProperty("md5")
private String md5;
@JsonProperty("md5_file")
private String md5File;
@JsonProperty("os")
private String os;
@JsonProperty("release_type")
private String releaseType;
@JsonProperty("sha1")
private String sha1;
@JsonProperty("sha1_file")
private String sha1File;
@JsonProperty("sha256")
private String sha256;
@JsonProperty("sha256_file")
private String sha256File;
@JsonProperty("sha512")
private String sha512;
@JsonProperty("sha512_file")
private String sha512File;
@JsonProperty("size")
private Integer size;
@JsonProperty("url")
private String url;
@JsonProperty("vendor")
private String vendor;
@JsonProperty("version")
private String version;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("architecture")
public String getArchitecture() {
return architecture;
}

@JsonProperty("architecture")
public void setArchitecture(String architecture) {
this.architecture = architecture;
}

@JsonProperty("features")
public List<Object> getFeatures() {
return features;
}

@JsonProperty("features")
public void setFeatures(List<Object> features) {
this.features = features;
}

@JsonProperty("file_type")
public String getFileType() {
return fileType;
}

@JsonProperty("file_type")
public void setFileType(String fileType) {
this.fileType = fileType;
}

@JsonProperty("filename")
public String getFilename() {
return filename;
}

@JsonProperty("filename")
public void setFilename(String filename) {
this.filename = filename;
}

@JsonProperty("image_type")
public String getImageType() {
return imageType;
}

@JsonProperty("image_type")
public void setImageType(String imageType) {
this.imageType = imageType;
}

@JsonProperty("java_version")
public String getJavaVersion() {
return javaVersion;
}

@JsonProperty("java_version")
public void setJavaVersion(String javaVersion) {
this.javaVersion = javaVersion;
}

@JsonProperty("jvm_impl")
public String getJvmImpl() {
return jvmImpl;
}

@JsonProperty("jvm_impl")
public void setJvmImpl(String jvmImpl) {
this.jvmImpl = jvmImpl;
}

@JsonProperty("md5")
public String getMd5() {
return md5;
}

@JsonProperty("md5")
public void setMd5(String md5) {
this.md5 = md5;
}

@JsonProperty("md5_file")
public String getMd5File() {
return md5File;
}

@JsonProperty("md5_file")
public void setMd5File(String md5File) {
this.md5File = md5File;
}

@JsonProperty("os")
public String getOs() {
return os;
}

@JsonProperty("os")
public void setOs(String os) {
this.os = os;
}

@JsonProperty("release_type")
public String getReleaseType() {
return releaseType;
}

@JsonProperty("release_type")
public void setReleaseType(String releaseType) {
this.releaseType = releaseType;
}

@JsonProperty("sha1")
public String getSha1() {
return sha1;
}

@JsonProperty("sha1")
public void setSha1(String sha1) {
this.sha1 = sha1;
}

@JsonProperty("sha1_file")
public String getSha1File() {
return sha1File;
}

@JsonProperty("sha1_file")
public void setSha1File(String sha1File) {
this.sha1File = sha1File;
}

@JsonProperty("sha256")
public String getSha256() {
return sha256;
}

@JsonProperty("sha256")
public void setSha256(String sha256) {
this.sha256 = sha256;
}

@JsonProperty("sha256_file")
public String getSha256File() {
return sha256File;
}

@JsonProperty("sha256_file")
public void setSha256File(String sha256File) {
this.sha256File = sha256File;
}

@JsonProperty("sha512")
public String getSha512() {
return sha512;
}

@JsonProperty("sha512")
public void setSha512(String sha512) {
this.sha512 = sha512;
}

@JsonProperty("sha512_file")
public String getSha512File() {
return sha512File;
}

@JsonProperty("sha512_file")
public void setSha512File(String sha512File) {
this.sha512File = sha512File;
}

@JsonProperty("size")
public Integer getSize() {
return size;
}

@JsonProperty("size")
public void setSize(Integer size) {
this.size = size;
}

@JsonProperty("url")
public String getUrl() {
return url;
}

@JsonProperty("url")
public void setUrl(String url) {
this.url = url;
}

@JsonProperty("vendor")
public String getVendor() {
return vendor;
}

@JsonProperty("vendor")
public void setVendor(String vendor) {
this.vendor = vendor;
}

@JsonProperty("version")
public String getVersion() {
return version;
}

@JsonProperty("version")
public void setVersion(String version) {
this.version = version;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}


}
