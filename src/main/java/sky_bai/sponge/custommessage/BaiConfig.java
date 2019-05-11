package sky_bai.sponge.custommessage;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaiConfig {
	static Path CustomMessageConfigPath;
	static Path configPath;
	static Map<String, CMObject> CMConfig = new HashMap<>();

	public static void setMessageSet() throws ObjectMappingException, IOException {
		CMConfig.clear();
		ConfigurationNode node = YAMLConfigurationLoader.builder().setPath(configPath).build().load();
		CMConfig.putAll(node.getValue(new TypeToken<Map<String, CMObject>>() {}, Collections.emptyMap()));
	}
}
