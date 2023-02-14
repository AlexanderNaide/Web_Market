package backupSecurityFiles.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //область видимости аннотации
@Target(ElementType.METHOD) //Указывает то, где можно ставить эту аннотацию
public @interface DoNotThrowBadCredentialsException {
}
