// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'gankio_entity.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GankioEntity _$GankioEntityFromJson(Map<String, dynamic> json) {
  return GankioEntity(
      json['error'] as bool,
      (json['results'] as List)
          ?.map((e) => e == null
              ? null
              : ResultsEntity.fromJson(e as Map<String, dynamic>))
          ?.toList());
}

Map<String, dynamic> _$GankioEntityToJson(GankioEntity instance) =>
    <String, dynamic>{'error': instance.error, 'results': instance.results};

ResultsEntity _$ResultsEntityFromJson(Map<String, dynamic> json) {
  return ResultsEntity(
      json['_id'] as String,
      json['createdAt'] as String,
      json['desc'] as String,
      json['publishedAt'] as String,
      json['source'] as String,
      json['type'] as String,
      json['url'] as String,
      json['used'] as bool,
      json['who'] as String);
}

Map<String, dynamic> _$ResultsEntityToJson(ResultsEntity instance) =>
    <String, dynamic>{
      '_id': instance.id,
      'createdAt': instance.createdAt,
      'desc': instance.desc,
      'publishedAt': instance.publishedAt,
      'source': instance.source,
      'type': instance.type,
      'url': instance.url,
      'used': instance.used,
      'who': instance.who
    };
