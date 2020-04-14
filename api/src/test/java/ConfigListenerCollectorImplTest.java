import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.ConfigModifiedListener;
import com.jsonmack.mcplugins.config.ConfigService;
import com.jsonmack.mcplugins.config.field.ConfigField;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.mcplugins.config.field.impl.LongConfigFieldListener;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Jason MK on 2020-03-20 at 10:59 p.m.
 */
@RunWith(JUnit4.class)
public class ConfigListenerCollectorImplTest implements ConfigModifiedListener<ConfigListenerCollectorImplTest.TestConfig> {

    @Test
    public void assertCollection() {
        TestConfig config = new TestConfig(1L, 1L);

        ConfigService<TestConfig> service = new ConfigService.Builder<>(config, this).build();

        Assert.assertEquals(2, service.getListeners().size());
    }

    @Override
    public void onModify(TestConfig config) {

    }

    public static class TestConfig implements Config {

        @ConfigField(TestConfigLongFieldListener.class)
        private final Long value;

        @ConfigField(TestConfigFieldListener.class)
        private final Long simpleValue;

        public TestConfig(Long value, Long simpleValue) {
            this.value = value;
            this.simpleValue = simpleValue;
        }
    }

    public static class TestConfigLongFieldListener implements LongConfigFieldListener<TestConfig> {

        @Override
        public ConfigFieldAcceptanceResult acceptable(Long value) {
            return ConfigFieldAcceptanceResult.ok();
        }

        @Override
        public TestConfig modify(TestConfig config, Long value) {
            return config;
        }
    }

    public static class TestConfigFieldListener implements ConfigFieldListener<TestConfig, Long> {

        @Override
        public ConfigFieldAcceptanceResult acceptable(Long value) {
            return ConfigFieldAcceptanceResult.ok();
        }

        @Override
        public Long parse(String argument) throws ConfigFieldListenerParseException {
            return Long.parseLong(argument);
        }

        @Override
        public TestConfig modify(TestConfig config, Long value) {
            return config;
        }
    }

}
