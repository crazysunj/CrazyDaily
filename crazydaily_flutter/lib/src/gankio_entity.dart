import 'package:json_annotation/json_annotation.dart';

part 'gankio_entity.g.dart';

@JsonSerializable()

class GankioEntity extends Object {
  @JsonKey(name: 'error')
  bool error;

  @JsonKey(name: 'results')
  List<ResultsEntity> results;

  GankioEntity(
    this.error,
    this.results,
  );


  @override
  String toString() {
    return 'GankioEntity{error: $error, results: $results}';
  }

  factory GankioEntity.fromJson(Map<String, dynamic> srcJson) =>
      _$GankioEntityFromJson(srcJson);

  Map<String, dynamic> toJson() => _$GankioEntityToJson(this);
}

@JsonSerializable()

class ResultsEntity extends Object {
  @JsonKey(name: '_id')
  String id;

  @JsonKey(name: 'createdAt')
  String createdAt;

  @JsonKey(name: 'desc')
  String desc;

  @JsonKey(name: 'publishedAt')
  String publishedAt;

  @JsonKey(name: 'source')
  String source;

  @JsonKey(name: 'type')
  String type;

  @JsonKey(name: 'url')
  String url;

  @JsonKey(name: 'used')
  bool used;

  @JsonKey(name: 'who')
  String who;

  ResultsEntity(
    this.id,
    this.createdAt,
    this.desc,
    this.publishedAt,
    this.source,
    this.type,
    this.url,
    this.used,
    this.who,
  );


  @override
  String toString() {
    return 'ResultsEntity{id: $id, createdAt: $createdAt, desc: $desc, publishedAt: $publishedAt, source: $source, type: $type, url: $url, used: $used, who: $who}';
  }

  factory ResultsEntity.fromJson(Map<String, dynamic> srcJson) =>
      _$ResultsEntityFromJson(srcJson);

  Map<String, dynamic> toJson() => _$ResultsEntityToJson(this);
}
