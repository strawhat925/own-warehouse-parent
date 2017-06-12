package code.warehouse.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import code.warehouse.common.annotation.Transparent;

/**
 * ${DESCRIPTION}
 * package code.warehouse.bytebuddy
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-06-09 15:05
 **/
public class WarehouseAgent {
    //private static final Logger logger = LoggerFactory.getLogger(WarehouseAgent.class);


    /**
     * Main entrance.
     * Use byte-buddy transform to enhance all classes.
     *
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {


        //logger.debug("start premain.");

        System.out.println(agentArgs);

        new AgentBuilder.Default()
                .type(ElementMatchers.isAnnotatedWith(Transparent.class))
                .transform(((builder, typeDescription, classLoader) -> builder.method(ElementMatchers.named("save"))
                        .intercept(MethodDelegation.to(LoginInterceptor.class).andThen(SuperMethodCall.INSTANCE)))
                ).with(new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("------==============================================================");
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule) {

            }
        }).installOn(instrumentation);
    }


    public static class LoginInterceptor {

        @RuntimeType
        public static Object intercept(@AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<?> callable) {
            long start = System.currentTimeMillis();

            try {
                return callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("parameter-->" + Arrays.asList(allArguments).toString());
                System.out.println("method name-->" + method.toString());
                System.out.println(System.currentTimeMillis() - start);
            }
            return null;
        }
    }

}
