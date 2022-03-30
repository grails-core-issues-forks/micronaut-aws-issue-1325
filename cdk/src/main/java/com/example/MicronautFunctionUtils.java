package com.example;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.constructs.Construct;

import java.util.Collections;
import java.util.Map;

public final class MicronautFunctionUtils {
    private static final String OPTIMIZED = "optimized";
    private static final String JAR = "jar";
    private static final String ALL = "all";
    private static final String DASH = "-";
    private static final String BUILD = "build";
    private static final String LIBS = "libs";
    private static final String DOTDOT = "..";
    private static final String SLASH = "/";
    private static final String LAMBDA = "lambda";
    private static final String ZIP = "zip";
    private static final String DOT = ".";
    private static final String VERSION_ZERO_ONE = "0.1";
    private static final String COMMA = ",";
    private static final String TARGET = "target";
    public static final String PROXY_HANDLER = "io.micronaut.function.aws.proxy.MicronautLambdaHandler";
    private static final String FUNCTION = "function";

    private MicronautFunctionUtils() {

    }

    private static String jarPath(BuildTool buildTool, String moduleName, String artifactId, boolean optimized, String version) {
        switch (buildTool) {
            case MAVEN:
                String filename = String.join(DOT, String.join(DASH, artifactId, version), JAR);
                return mavenArtifact(moduleName, filename);
            case GRADLE:
            default:
                return gradleArtifact(moduleName, gradleJarFilename(optimized, artifactId, version));
        }
    }

    private static String gradleJarFilename(boolean optimized, String artifactId, String version) {
        return optimized ?
                String.join(DOT, String.join(DASH, artifactId, version, ALL, OPTIMIZED), JAR) :
                String.join(DOT, String.join(DASH, artifactId, version, ALL), JAR);
    }

    private static String jarPath(BuildTool buildTool, String moduleName, String artifactId, boolean optimized) {
        return jarPath(buildTool, moduleName, artifactId, optimized, VERSION_ZERO_ONE);
    }

    private static String jarPath(BuildTool buildTool, String moduleName, boolean optimized) {
        return jarPath(buildTool, moduleName, moduleName, optimized, VERSION_ZERO_ONE);
    }

    public static String jarPath(BuildTool buildTool, String moduleName, String artifactId) {
        return jarPath(buildTool, moduleName, artifactId, false, VERSION_ZERO_ONE);
    }

    private static String jarPath(BuildTool buildTool, String moduleName) {
        return jarPath(buildTool, moduleName, moduleName, false, VERSION_ZERO_ONE);
    }

    private static String mavenArtifact(String moduleName, String filename) {
        return String.join(SLASH, DOTDOT, moduleName, TARGET, filename);
    }

    private static String gradleArtifact(String moduleName, String filename) {
        return String.join(SLASH, DOTDOT, moduleName, BUILD, LIBS, filename);
    }

    private static String providedZipPath(BuildTool buildTool, String moduleName, boolean optimized, String version) {
        switch (buildTool) {
            case MAVEN:
                String filename = String.join(DOT, FUNCTION, ZIP);
                return mavenArtifact(moduleName, filename);

            case GRADLE:
            default:
                return gradleArtifact(moduleName, gradleZipFilename(optimized, moduleName, version));
        }
    }

    private static String gradleZipFilename(boolean optimized, String moduleName, String version) {
        return optimized ?
                String.join(DOT, String.join(DASH, moduleName, version, OPTIMIZED, LAMBDA), ZIP) :
                String.join(DOT, String.join(DASH, moduleName, version, LAMBDA), ZIP);
    }

    private static String providedZipPath(BuildTool buildTool, String moduleName, boolean optimized) {
        return providedZipPath(buildTool, moduleName, optimized, VERSION_ZERO_ONE);
    }

    public static String providedZipPath(BuildTool buildTool, String moduleName) {
        return providedZipPath(buildTool, moduleName, false, VERSION_ZERO_ONE);
    }

    private static Function.Builder createFunctionBuilder(Construct scope,
                                                          String id,
                                                          String codePath,
                                                          String handler,
                                                          Map<String, String> environmentVariables) {
        return Function.Builder.create(scope, id)
                .environment(environmentVariables)
                .code(Code.fromAsset(codePath))
                .handler(handler)
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .tracing(Tracing.ACTIVE);
    }

    public static Function createFunctionProvided(Construct scope,
                                                  String id,
                                                  String codePath,
                                                  String handler) {
        return createFunctionProvided(scope, id, codePath, handler, Collections.emptyMap());
    }

    public static Function createFunctionProvided(Construct scope,
                                                   String id,
                                                   String codePath,
                                                   String handler,
                                                   Map<String, String> environmentVariables) {
        return createFunctionBuilder(scope, id, codePath, handler, environmentVariables)
                .runtime(Runtime.PROVIDED_AL2)
                .build();
    }

    public static Function createFunctionJava(Construct scope,
                                              String id,
                                              String codePath,
                                              String handler) {
        return createFunctionJava(scope, id, codePath, handler, Collections.emptyMap());
    }

    public static Function createFunctionJava(Construct scope,
                                              String id,
                                              String codePath,
                                              String handler,
                                              Map<String, String> environmentVariables) {
        return createFunctionBuilder(scope, id, codePath, handler, environmentVariables)
                .runtime(Runtime.JAVA_11)
                .architecture(Architecture.X86_64)
                .build();
    }
}

