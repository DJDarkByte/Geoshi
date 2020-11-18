package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class GeoshiJsonAdapterFactory : JsonAdapter.Factory {

  override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
    val adapter = when (type) {
      Position::class.java -> {
        PositionJsonAdapter()
      }
      Point::class.java -> {
        PointJsonAdapter(moshi.adapter(Position::class.java))
      }
      LineString::class.java -> {
        LineStringJsonAdapter(moshi.adapter(Position::class.java))
      }
      Polygon::class.java -> {
        PolygonJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiPoint::class.java -> {
        MultiPointJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiLineString::class.java -> {
        MultiLineStringJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiPolygon::class.java -> {
        MultiPolygonJsonAdapter(moshi.adapter(Position::class.java))
      }
      GeometryCollection::class.java -> {
        GeometryCollectionJsonAdapter(
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java)
        )
      }
      Feature::class.java -> {
        val wildCardAdapter = moshi.adapter<Any>(Any::class.java)
        FeatureJsonAdapter(
          wildCardAdapter,
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java),
          moshi.adapter(GeometryCollection::class.java)
        )
      }
      FeatureCollection::class.java -> {
        FeatureCollectionJsonAdapter(moshi.adapter(Feature::class.java))
      }
      else -> null
    }
    return adapter?.nullSafe()
  }


}
