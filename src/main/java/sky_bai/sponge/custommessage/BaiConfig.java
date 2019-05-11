package sky_bai.sponge.custommessage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public class BaiConfig {
	static Path CustomMessageConfigPath;
	static Path configPath;
	static Set<String> CMCSet = new HashSet<>();
	static Map<String, Map<String, Object>> CMConfig = new HashMap<>();
	
	protected static ConfigurationNode getConfig(Path path) {
		try {
			return YAMLConfigurationLoader.builder().setPath(path).build().load();
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void setMessageSet() {
		CMCSet.clear();
		CMConfig.clear();
		for (String string : ((HashMap<String,Object>)getConfig(configPath).getValue()).keySet()) {
			CMCSet.add(string);
		}
		for (String CMC : CMCSet) {
			Map<String, Object> a1 =  new HashMap<>();
			ConfigurationNode a2 = getConfig(configPath).getNode(CMC);
			a1.put("Title", a2.getNode("Title").getString(""));
			a1.put("Padding", a2.getNode("Padding").getString("="));
			a1.put("Page", a2.getNode("Page").getInt(0));
			a1.put("Contents", a2.getNode("Contents").getValue());
			CMConfig.put(CMC, a1);
		}
	}
}
