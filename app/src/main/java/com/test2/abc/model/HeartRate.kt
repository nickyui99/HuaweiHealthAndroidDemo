package com.test2.abc.model

data class HeartRate(
    val group: List<Group>
)

data class Group(
    val startTime: Long,
    val endTime: Long,
    val sampleSet: List<SampleSet>
)

data class SampleSet(
    val dataCollectorId: String,
    val samplePoints: List<SamplePoint>
)

data class SamplePoint(
    val startTime: Long,
    val endTime: Long,
    val dataTypeName: String,
    val originalDataCollectorId: String,
    val value: List<Value>
)

data class Value(
    val fieldName: String,
    val floatValue: Float
)
