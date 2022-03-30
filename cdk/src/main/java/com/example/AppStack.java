package com.example;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.IFunction;
import software.constructs.Construct;

public class AppStack extends Stack {
    public static final String ARTIFACT_ID_FUNCTION = "function";
    public static final String ARTIFACT_ID_APP = "app";
    public static final String MODULE_APP = "app";
    public static final String MODULE_FUNCTION = "function";

    public AppStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public AppStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Function func = MicronautFunctionUtils.createFunctionJava(this,
                MODULE_FUNCTION,
                MicronautFunctionUtils.jarPath(BuildTool.GRADLE, MODULE_FUNCTION, ARTIFACT_ID_FUNCTION),
                "com.example.FunctionRequestHandler");

        Function app = MicronautFunctionUtils.createFunctionJava(this,
                MODULE_APP,
                MicronautFunctionUtils.jarPath(BuildTool.GRADLE, MODULE_APP, ARTIFACT_ID_APP),
                MicronautFunctionUtils.PROXY_HANDLER);

        LambdaRestApi lambdaRestApi = createLambdaRestApi(this, "demoapi", app);

        CfnOutput.Builder.create(this, "ApiUrl")
                .exportName("ApiUrl")
                .value(lambdaRestApi.getUrl())
                .build();
    }

    private static LambdaRestApi createLambdaRestApi(Construct scope, String id, IFunction handler) {
        return LambdaRestApi.Builder.create(scope, id)
                .handler(handler)
                .build();
    }
}
