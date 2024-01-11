package com.test2.abc

object Constants {
    //VARIABLEs
    const val APP_ID = 102726023


    //URLs
    const val FIREBASE_BASE_URL = "https://us-central1-healthkitdemoapi.cloudfunctions.net/health_demo"

    const val FIREBASE_AUTHORIZE_HEALTH_REDIRECT_URL = FIREBASE_BASE_URL + "/redirect"

    const val HUAWEI_AUTHORIZE_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/authorize"

    const val HUAWEI_UNAUTHORIZE_URL = "https://health-api.cloud.huawei.com/healthkit/v2/consents/${this.APP_ID}?deleteData=true"

    //HUAWEI HEALTH SCOPE
    const val HUAWEI_HEALTH_STEP_SCOPE = "https://www.huawei.com/healthkit/step.read"
    const val HUAWEI_HEART_RATE_READ_SCOPE = "https://www.huawei.com/healthkit/heartrate.read"

    //SHARED PREFs
    const val SP_ACCESS_TOKEN = "ACCESS_TOKEN"
}