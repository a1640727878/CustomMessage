package sky_bai.sponge.custommessage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public class BaiConfig {
	static Path CustomMessageConfigPath;
	static Path configPath;
	static Set<String> mesName = new HashSet<String>();
	static Map<String, List<String>> mes = new HashMap<String, List<String>>();
	
	protected static ConfigurationNode getConfig(Path path) {
		try {
			return YAMLConfigurationLoader.builder().setPath(path).build().load();
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void setMessageSet() {
		mesName.clear();
		mesName.addAll(((HashMap<String,Object>)getConfig(configPath).getValue()).keySet());	
		for (String mesNameString : mesName) {
			mes.put(mesNameString, (List<String>)BaiConfig.getConfig(BaiConfig.configPath).getNode(mesNameString,"Contents").getValue());
		}
	}
}
