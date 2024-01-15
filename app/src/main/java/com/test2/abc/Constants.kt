package com.test2.abc

object Constants {
    //VARIABLEs
    const val APP_ID = 102726023

    //URLs
    const val FIREBASE_BASE_URL = "https://us-central1-healthkitdemoapi.cloudfunctions.net/health_demo"
    const val FIREBASE_REFRESH_HUAWEI_HEALTH_TOKEN_URL = FIREBASE_BASE_URL + "/refresh_huawei_health_token"
    const val FIREBASE_AUTHORIZE_HEALTH_REDIRECT_URL = FIREBASE_BASE_URL + "/redirect"
    const val HUAWEI_AUTHORIZE_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/authorize"
    const val HUAWEI_UNAUTHORIZE_URL = "https://health-api.cloud.huawei.com/healthkit/v2/consents/${this.APP_ID}?deleteData=false"
    const val HUAWEI_DATA_COLLECTOR_URL = "https://health-api.cloud.huawei.com/healthkit/v2/dataCollectors"
    const val HUAWEI_POLYMERIZE_SAMPLE_URL = "https://health-api.cloud.huawei.com/healthkit/v2/sampleSet:polymerize"
    const val HUAWEI_DAILY_POLYMERIZE_SAMPLE_URL = "https://health-api.cloud.huawei.com/healthkit/v2/sampleSet:dailyPolymerize"

    //HUAWEI HEALTH SCOPE
    const val HUAWEI_HEALTH_STEP_READ_SCOPE = "https://www.huawei.com/healthkit/step.read"
    const val HUAWEI_HEALTH_STEP_WRITE_SCOPE = "https://www.huawei.com/healthkit/step.write"
    const val HUAWEI_HEALTH_STEP_BOTH_SCOPE = "https://www.huawei.com/healthkit/heartrate.both"
    const val HUAWEI_HEART_RATE_READ_SCOPE = "https://www.huawei.com/healthkit/heartrate.read"
    const val HUAWEI_HEART_RATE_WRITE_SCOPE = "https://www.huawei.com/healthkit/heartrate.write"
    const val HUAWEI_HEART_RATE_BOTH_SCOPE = "https://www.huawei.com/healthkit/heartrate.both"
    const val HUAWEI_HEALTH_DATA_ONE_YEAR_SCOPE = "https://www.huawei.com/healthkit/historydata.open.year"

    //HUAWEI HEALTH DATA TYPEs
    const val HUAWEI_DT_CONTINUOUS_STEP_DELTA = "com.huawei.continuous.steps.delta"
    const val HUAWEI_DT_CONTINUOUS_STEP_TOTAL = "com.huawei.continuous.steps.total"
    const val HUAWEI_DT_INSTANTANEOUS_HEART_RATE = "com.huawei.instantaneous.heart_rate"
    const val HUAWEI_DT_CONTINUOUS_HEART_RATE = "com.huawei.continuous.heart_rate.statistics"

    //SHARED PREFs
    const val SP_ACCESS_TOKEN = "ACCESS_TOKEN"
}