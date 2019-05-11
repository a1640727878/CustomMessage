package sky_bai.sponge.custommessage;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;

@Plugin(id = "custommessage", name = "CustomMessage")
public class CustomMessage {

	public final static Logger logger = LoggerFactory.getLogger("CustomMessage");

	@Listener
	public void onServerStart(GamePreInitializationEvent event) {
	}
	
	static public void setConfig() throws IOException, ObjectMappingException {
		BaiConfig.setMessageSet();
	}

	public static Text deserializeToText(String s) {
		return TextSerializers.JSON.deserializeUnchecked(s);
	}

	final private void setConfigPath() {
		BaiConfig.CustomMessageConfigPath = Sponge.getConfigManager().getPluginConfig(this).getDirectory().resolveSibling("CustomMessag");
		BaiConfig.configPath = BaiConfig.CustomMessageConfigPath.resolve("config.yml");
	}

	@Listener
	public void loadConfig(GameLoadCompleteEvent event) {
		try {
			logger.info("插件加载中....");
			this.setConfigPath();
			this.setConfigFile();
			CustomMessage.setConfig();
			logger.info("插件加载完成.");
		} catch (Exception e) {
			logger.error("Error", e);
		}
		CommandSpec reload = CommandSpec.builder()
				.permission("CustomMessage.plugin.reload")
				.executor((src, args) -> {
					try {
						CustomMessage.setConfig();
						src.sendMessage(Text.of("§l[CustomMessage]§r插件重载完成"));
						return CommandResult.success();
					} catch (Exception e) {
						logger.error("Failed to reload config.", e);
						throw new CommandException(Text.of("Failed to reload config."), e);
					}
				}).build();
		CommandElement argCMObject = GenericArguments.choices(Text.of("message"), () -> BaiConfig.CMConfig.keySet(), key -> BaiConfig.CMConfig.get(key), false);
		CommandSpec send = CommandSpec.builder()
				.permission("CustomMessage.plugin.send")
				.arguments(argCMObject)
				.executor((src, args) -> {
					args.<CMObject>getOne("message").get().sendTo(src);
					return CommandResult.success();
				}).build();
		CommandSpec sendTo = CommandSpec.builder()
				.permission("CustomMessage.plugin.sendto")
				.arguments(GenericArguments.player(Text.of("player")), argCMObject)
				.executor((src, args) -> {
					CMObject cmObject = args.<CMObject>getOne("message").get();
					args.<Player>getAll("player").forEach(cmObject::sendTo);
					return CommandResult.success();
				}).build();
		CommandSpec baiCommand = CommandSpec.builder()
				.permission("CustomMessage.plugin.commands")
				.child(reload, "reload")
				.child(send, "send")
				.child(sendTo, "sendto")
				.build();
		Sponge.getCommandManager().register(this, baiCommand, "CustomMessage", "CM");
	}

	final private void setConfigFile() throws IOException {
		Sponge.getAssetManager().getAsset(this, "config.yml").get()
				.copyToDirectory(BaiConfig.CustomMessageConfigPath, false, true);
	}
}
