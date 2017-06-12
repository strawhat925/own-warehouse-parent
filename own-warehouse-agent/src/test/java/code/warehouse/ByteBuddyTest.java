package code.warehouse;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * ${DESCRIPTION}
 * package dynamic.bytebuddy
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-06-09 14:05
 **/
@SuppressWarnings("ALL")
public class ByteBuddyTest {


    @Test
    public void testDynamicType() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("hello world!"))
                .make()
                .load(ByteBuddyTest.class.getClassLoader())
                .getLoaded();


        assertThat(dynamicType.newInstance().toString(), is("hello world!111"));
    }


    /**
     * for java 8
     */
    @Test
    public void testDynamicTypeInterceptor() throws IllegalAccessException, InstantiationException {
        Class<? extends SubGeneral> dynamicType = new ByteBuddy()
                //.subclass(java.util.function.Function.class)
                .subclass(SubGeneral.class)
                .method(ElementMatchers.named("apply"))
                //.intercept(MethodDelegation.to(new GreetingInterceptor()))
                .intercept(MethodDelegation.to(new GeneralInterceptor()))
                .make()
                .load(ByteBuddyTest.class.getClassLoader())
                .getLoaded();


        assertThat(dynamicType.newInstance().apply("Byte Buddy"), is("Hello from Byte Buddy"));

    }


    public static class GreetingInterceptor {
        public Object greet(Object argument) {
            return "Hello from " + argument;
        }
    }


    public interface GeneralInter {
        public void sysHello();
    }


    public static class SubGeneral {


        public String apply(String name) {
            System.out.println(name + "====================");

            return name;
        }
    }


    public static class GeneralInterceptor {

        @RuntimeType
        public Object intercept(@This Object object, @AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<String> callable) {
            // intercept any method of any signature
            System.out.println("parameter --> " + Arrays.asList(allArguments).toString());
            System.out.println("method name --> " +method.getName());
            System.out.println("this --> " +object);
            System.out.println("call method --> " +
                    "" + callable);

            String result = null;
            try {
                result = callable.call();
                System.out.println(result + "#################");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Hello from " + result;
        }
    }

}
