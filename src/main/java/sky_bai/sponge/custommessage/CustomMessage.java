package sky_bai.sponge.custommessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "custommessage", name = "CustomMessage")
public class CustomMessage {

	public final static Logger logger = LoggerFactory.getLogger("CustomMessage");;

	@Listener
	public void onServerStart(GamePreInitializationEvent event) {
		logger.info("插件加载中....");
	}
	
	@Listener
	public void loadConfig(GameLoadCompleteEvent event) {
		this.setConfigPath();
		this.setConfigFile();
		CustomMessage.setConfig();
		Sponge.getCommandManager().register(this, new BaiCommand(), "CustomMessage", "CM");
		logger.info("插件加载完成.");
	}

	final private void setConfigFile() {
		try {
			Sponge.getAssetManager().getAsset(this, "config.yml").get().copyToDirectory(BaiConfig.CustomMessageConfigPath, false, true);
		} catch (Exception e1) {
		}
	}

	final private void setConfigPath() {
		BaiConfig.CustomMessageConfigPath = Sponge.getConfigManager().getPluginConfig(this).getDirectory().resolveSibling("CustomMessag");
		BaiConfig.configPath = BaiConfig.CustomMessageConfigPath.resolve("config.yml");
	}

	static public void setConfig() {
		BaiConfig.setMessageSet();
	}
}
